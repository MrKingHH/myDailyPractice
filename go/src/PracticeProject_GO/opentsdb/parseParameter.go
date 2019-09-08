package main

import (
	"encoding/json"
	"fmt"
	"github.com/pkg/errors"
	"io/ioutil"
	"net"
	"net/http"
	"reflect"
	"strconv"
	"strings"
	"time"
)

//定义自己的路由器
type OpentsdbHandler struct {
}

type TagFilter struct {
	Type    string `json:"type"`
	Tagk    string `json:"tagk"`
	Filter  string `json:"filter"`
	GroupBy bool   `json:"groupBy"`
}

type TSSubQuery struct {
	Aggregator   string            `json:"aggregator"`
	Metric       string            `json:"metric"`
	Downsample   string            `json:"downsample,omitempty"`
	Tags         map[string]string `json:"tags,omitempty"`
	Filters      []*TagFilter      `json:"filters,omitempty"`
	ExplicitTags bool              `json:"explicitTags,omitempty"`
}

type TSQuery struct {
	Start        interface{}   `json:"start"`
	End          interface{}   `json:"end"`
	Queries      []*TSSubQuery `json:"queries"`
	MsResolution bool          `json:"msResolution,omitempty"`
	Method       string        `json:"method,omitempty"`
}

/**
 * Converts the  map to a filter list. If a filter already exists for a
 * tag group by and we're told to process group bys, then the duplicate
 * is skipped.
 */
/*
入参类似于{colo=regexp(sjc.*)}, filters目前为[]
colo对应tagk regexp对应type sjc.* 对应filter
*/
func parseMapToFilters(filter_map map[string]string, filters *[]*TagFilter, group_by bool) {
	if filter_map == nil {
		return
	}

	//将map中的k v放入filters中
	for k, v := range filter_map {
		filter := getFilter(k, v)

		if group_by {
			filter.GroupBy = true
		} else {
			filter.GroupBy = false
		}
		//使用filters = append(filters, filter)对原slice的修改不起作用
		*filters = append(*filters, filter)
	}
}

//v可能是纯字符串类型 也可能是regexp(sjc.*类型)
func getFilter(key string, value string) *TagFilter {
	filter := &TagFilter{}
	filter.Tagk = key

	//获取字符串长度
	length := len(value)

	leftBracketIndex := strings.Index(value, "(")
	//表示没有括号，纯字符类型,默认是liter_or类型,如果有*号，则是通配符型
	if leftBracketIndex == -1 {

		if strings.Contains(value, "*") {
			filter.Type = "wildcard"
			filter.Filter = value
		} else {
			filter.Type = "liter_or"
			filter.Filter = value
		}

	} else {
		filter.Type = value[:leftBracketIndex]
		filter.Filter = value[leftBracketIndex+1 : length-1]
	}

	return filter
}

func (dataQuery *TSQuery) validateQuery() error {

	//获取到具体的类型，可能是string类型或者float64
	start := reflect.TypeOf(dataQuery.Start).String()
	end := reflect.TypeOf(dataQuery.End).String()
	var startTime int64
	var endTime int64
	//只有POST方式才需要判断是string类型还是float类型。
	if dataQuery.Method == "POST" {
		if start == "string" {
			//使用类型断言转成具体类型
			startTimeStr := dataQuery.Start.(string)
			//调用相对转绝对的函数 parseRelativeTime(relative string) float64
			startTime, _ = parseRelativeTime(startTimeStr)
		} else if start == "float64" {
			//使用类型断言转成具体类型
			startTime = int64(dataQuery.Start.(float64))
		}

		if end == "string" {
			//使用类型断言转成具体类型
			endTimeStr := dataQuery.End.(string)

			//调用相对转绝对的函数 parseRelativeTime(relative string) float64
			endTime, _ = parseRelativeTime(endTimeStr)

		} else if end == "float64" {
			//使用类型断言转成具体类型
			endTime = int64(dataQuery.End.(float64))
		}
	} else if dataQuery.Method == "GET" {
		startTime = dataQuery.Start.(int64)
		endTime = dataQuery.End.(int64)
	}

	if endTime <= startTime {
		return errors.New(fmt.Sprintf("End time [%d] must greater than start time [%d]", endTime, startTime))
	}

	if dataQuery.Queries == nil {
		return errors.New("Missing queries")
	}

	for _, value := range dataQuery.Queries {
		if err := value.validateQuery(); err != nil {
			return err
		}
	}

	return nil
}

func (subQuery *TSSubQuery) validateQuery() error {
	if subQuery.Aggregator == "" {
		return errors.New("Missing the aggregation function")
	}

	if subQuery.Metric == "" {
		return errors.New("Missing the metric")
	}

	if subQuery.Downsample == "" {
		subQuery.Downsample = "none"
	}
	return nil
}

func parseTagToFilters(filter_map map[string]string, filters *[]*TagFilter) {
	parseMapToFilters(filter_map, filters, true)
}

func (h *OpentsdbHandler) serveQuery(w http.ResponseWriter, r *http.Request) {

	if r.Method != "POST" && r.Method != "GET" && r.Method != "DELETE" {
		http.Error(w, http.StatusText(http.StatusMethodNotAllowed), http.StatusMethodNotAllowed)
		return
	}

	if r.Method == "POST" {
		h.servePostQuery(w, r)
	}

	if r.Method == "GET" {
		h.serveGetQuery(w, r)
	}
}

func (h *OpentsdbHandler) servePostQuery(w http.ResponseWriter, r *http.Request) {
	query, _ := parsePostQuery(w, r)
	if err := query.validateQuery(); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
	}

	jsonData, _ := json.MarshalIndent(query, " ", " ")
	fmt.Printf("%s\n", jsonData)
	w.Header().Set("Content-Type", "application/json")
	fmt.Fprintf(w, "%s", jsonData)
}

func parsePostQuery(w http.ResponseWriter, r *http.Request) (*TSQuery, error) {
	//得到序列化的字节流
	result, _ := ioutil.ReadAll(r.Body)

	//将字节流反序列化成对象。将json数据格式转成对象格式
	query := &TSQuery{}
	if err := json.Unmarshal(result, query); err != nil {
		return nil, errors.New(fmt.Sprintf("Json unmarshaling failed: %s", err))
	}
	query.Method = "POST"

	return query, nil
}

/**
 * Parses a query string legacy style query from the URI
 */

func (h *OpentsdbHandler) serveGetQuery(w http.ResponseWriter, r *http.Request) {
	query, _ := parseGetQuery(w, r)
	if err := query.validateQuery(); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
	}

	jsonData, _ := json.MarshalIndent(query, " ", " ")
	fmt.Printf("%s\n", jsonData)
	w.Header().Set("Content-Type", "application/json")
	fmt.Fprintf(w, "%s", jsonData)
}

func parseGetQuery(w http.ResponseWriter, r *http.Request) (*TSQuery, error) {
	data_query := &TSQuery{}
	//标记该查询为GET方式
	data_query.Method = "GET"
	startTime := r.FormValue("start")
	if startTime != "" {
		//试图将第一位字符转成数字
		_, errStart := strconv.Atoi(string(startTime[0]))
		if errStart != nil {
			return nil, errors.New("Start time must be an integer")
		}

		//转换成整型出错了就是类似于相对1h-ago之类的相对时间
		if startTimeInt, err := strconv.Atoi(startTime); err != nil {
			//此处应该对1h-ago之类的时间进行单独处理，获取到绝对的开始时间
			data_query.Start, err = parseRelativeTime(startTime)
			if err != nil {
				return nil, err
			}
		} else {
			data_query.Start = int64(startTimeInt)
		}

	} else {
		return nil, errors.New("Missing start time")
	}

	//结束时间可能是绝对时间和相对时间
	endTime := r.FormValue("end")
	if endTime != "" {
		_, errEnd := strconv.Atoi(string(endTime[0]))
		if errEnd != nil {
			return nil, errors.New("End time must be an integer")
		}

		if endTimeInt, err := strconv.Atoi(endTime); err != nil {
			data_query.End, err = parseRelativeTime(endTime)
			if err != nil {
				return nil, err
			}
		} else {
			data_query.End = int64(endTimeInt)
		}
	} else {
		data_query.End = int64(time.Now().Unix())
	}

	msResolution := r.FormValue("ms")
	if msResolution != "" {
		if msResolution == "true" {
			data_query.MsResolution = true
		}

		if msResolution == "false" {
			data_query.MsResolution = false
		}
	} else { //默认是false
		data_query.MsResolution = false
	}

	mTypeParam := r.URL.Query()["m"]
	if mTypeParam != nil {
		for _, single_query := range mTypeParam {
			if err := parseMTypeSubQuery(single_query, data_query); err != nil {
				return nil, err
			}
		}
	}

	return data_query, nil
}

func getTimeAndUnit(time1 string) (int64, string) {
	length := len(time1)
	var unit string
	var timeIntTemp int

	second := time1[length-2]
	//如果倒数第二位是字母
	if _, err := strconv.Atoi(string(second)); err != nil {
		unit = time1[length-2 : length-1]
		//取出前面的数字
		timeNum := time1[:length-2]
		timeIntTemp, _ = strconv.Atoi(timeNum)
	} else {
		//取出最后一位字符
		unit = string(time1[length-1])
		//取出前面的数字
		timeNum := time1[:length-1]
		timeIntTemp, _ = strconv.Atoi(timeNum)
	}

	return int64(timeIntTemp), unit
}

//拆分相对时间字符串
func relaDepart(str string, relstr []string) {
	for i, v := range strings.Split(str, "-") {
		relstr[i] = v
		i++
	}
}

func parseRelativeTime(relativeTime string) (int64, error) {
	//是否以数字开头
	if !(relativeTime[0] >= '0' && relativeTime[0] <= '9') {
		return 0, errors.New("start/end_time must start with integer")
	}

	//存放拆分后的相关时间
	var relativeArray = make([]string, 2)
	relaDepart(relativeTime, relativeArray)

	//ago部分
	if relativeArray[1] != "ago" {
		return 0, errors.New("wrong relative time formate")
	}

	//30m部分
	timeInt, unit := getTimeAndUnit(relativeArray[0])
	var finaltime int64
	//判断时间字母是否合规
	switch unit {
	case "ms":
		finaltime = time.Now().Unix() - timeInt/1000
		break
	case "s":
		finaltime = time.Now().Unix() - timeInt
		break
	case "m":
		finaltime = time.Now().Unix() - timeInt*60
		break
	case "h":
		finaltime = time.Now().Unix() - timeInt*60*60
		break
	case "d":
		finaltime = time.Now().Unix() - timeInt*60*60*24
		break
	case "w":
		finaltime = time.Now().Unix() - timeInt*60*60*24*7
		break
	case "n":
		finaltime = time.Now().Unix() - timeInt*60*60*24*30
		break
	case "y":
		finaltime = time.Now().Unix() - timeInt*60*60*24*365
		break
	default:
		return 0, errors.New("wrong relative time")
	}

	return finaltime, nil
}

/**
 * Parses a query string "m=..." type query and adds it to the TSQuery.
 * This will generate a TSSubQuery and add it to the TSQuery if successful
 */
/*
入参是参数m后面的querystring，已经部分被初始化的TSQuery实例
*/
func parseMTypeSubQuery(query_string string, data_query *TSQuery) error {
	if query_string == "" {
		return errors.New("The query string was empty")
	}
	/*
		 m=
		<aggregator>:
		[<down_sampler>:]
		[explicit_tags:]
		<metric_name>[{<tag_name1>=<groupingfilter>[,...<tag_nameN>=<grouping_filter>]}][{<tag_name1>=<non grouping filter>[,...<tag_nameN>=<non_grouping_filter>]}]
	*/
	parts := strings.Split(query_string, ":")
	i := len(parts)
	if i < 2 || i > 4 {
		var message string
		if i < 2 {
			message = "not enough"
		} else {
			message = "too many"
		}
		return errors.Errorf("Invalid parameter m=%s, (%s :-separated parts)", query_string, message)
	}

	sub_query := &TSSubQuery{}
	// the aggregator is first
	sub_query.Aggregator = parts[0]

	// Move to the last part (the metric name).
	//解析metric部分的信息，包括metric名和过滤器
	i--
	filters := make([]*TagFilter, 0)
	metricName, err := parseWithMetricAndFilters(parts[i], &filters)
	if err != nil {
		return err
	}
	sub_query.Filters = filters
	sub_query.Metric = metricName

	// parse out the downsampler
	for x := 1; x < len(parts)-1; x++ {
		//把每一个剩余的部分转成rune形式
		temp := []rune(parts[x])
		//如果这个部分的第一个字符是0，说明是downsample
		if _, err := strconv.Atoi(string(temp[0])); err == nil {
			sub_query.Downsample = parts[x]
		} else if strings.HasPrefix(parts[x], "explicit_tags") {
			sub_query.ExplicitTags = true
		}
	}

	data_query.Queries = append(data_query.Queries, sub_query)

	return nil
}

/**
 * Parses the metric and tags out of the given string.
 * @param metric A string of the form "metric" or "metric{tag=value,...}" or
 * now "metric{groupby=filter}{filter=filter}".
 */
func parseWithMetricAndFilters(metric_section string, filters *[]*TagFilter) (string, error) {

	if metric_section == "" {
		return "", errors.New("Metric cannot be null or empty")
	}

	if filters == nil {
		return "", errors.New("Filters cannot be null")
	}

	//获取第一个左大括号的位置
	firstLeftCurly := strings.Index(metric_section, "{")
	if firstLeftCurly < 0 {
		return metric_section, nil
	}

	temp := []rune(metric_section)
	length := len(metric_section)
	// 获取最后一个字符
	if string(temp[length-1]) != "}" { // "foo{"
		return "", errors.New(fmt.Sprintf("Missing '}' at the end of: %s", metric_section))
	} else if firstLeftCurly == length-2 { //foo{}
		return metric_section[:length-2], nil
	}

	//获取第一个右大括号位置
	firstRightCurly := strings.Index(metric_section, "}")
	filter_map := make(map[string]string)

	//deal with last {}
	if firstRightCurly != length-1 { // "foo{...}{tagk=filter}"
		//获取最后一个左大括号的位置
		lastLeftCurly := strings.LastIndex(metric_section, "{")
		//get the last {tagk = tagv}
		secondFilter := metric_section[lastLeftCurly+1 : length-1]
		secondFilterSplit := strings.Split(secondFilter, ",")
		for _, singleFilter := range secondFilterSplit {
			filter_map = make(map[string]string)
			if singleFilter == "" {
				break
			}

			if err := parseTagToMap(singleFilter, filter_map); err != nil {
				return "", err
			}

			//后面一个大括号不分组
			//函数返回时， 没解析到filters中去
			parseMapToFilters(filter_map, filters, false)
		}
	}

	//deal with first {}
	//substring the tags out of "foo{a=b,...,x=y}" and parse them.
	//first get {a=b,...,x=y}
	firstFilter := metric_section[firstLeftCurly+1 : firstRightCurly]
	firstFilterSplit := strings.Split(firstFilter, ",")

	for _, singleTag := range firstFilterSplit {
		filter_map = make(map[string]string)
		if singleTag == "" && firstRightCurly != length-1 {
			break
		}
		//将a=b转成<a,b>形式
		err := parseTagToMap(singleTag, filter_map)
		if err != nil {
			return "", err
		}

		//只有一个大括号表示分组
		parseTagToFilters(filter_map, filters)
	}

	return metric_section[0:firstLeftCurly], nil
}

/**
 * Parse a tag pair into a Map.
 */
func parseTagToMap(tag string, tagMap map[string]string) error {
	kv := strings.Split(tag, "=")
	if len(kv) != 2 || len(kv[0]) <= 0 || len(kv[1]) <= 0 {
		return errors.New(fmt.Sprintf("invalic tag: %s", tag))
	}

	if kv[0] == tagMap[kv[0]] {
		return nil
	}

	if tagMap[kv[0]] != "" {
		return errors.New(fmt.Sprintf("duplicate tag: %s , tags = %v", tag, tagMap))
	}

	tagMap[kv[0]] = kv[1]
	return nil
}

//实现http.Handler这个接口的唯一方法
func (h *OpentsdbHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	urlPath := r.URL.Path
	switch urlPath {
	case "/api/query":
		h.serveQuery(w, r)
	case "/api/v1/query":
		h.serveQuery(w, r)
	default:
		http.Error(w, "没有此url路径", http.StatusBadRequest)
	}
}

func main() {
	//实例化路由器Handler
	h := &OpentsdbHandler{}
	//基于TCP服务监听8088端口
	ln, err := net.Listen("tcp", ":8088")
	if err != nil {
		fmt.Printf("设置监听端口出错...")
	}
	//调用http.Serve(l net.Listener, handler Handler)方法，启动监听
	err1 := http.Serve(ln, h)
	if err1 != nil {
		fmt.Printf("启动监听出错")
	}
}
