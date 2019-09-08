package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net"
	"net/http"
	"reflect"
	"strconv"
)

//定义自己的路由器
type MyMux struct {
}

type point struct {
	Metric    string            `json:"metric"`
	TimeStamp int64             `json:"timestamp"`
	Value     float64           `json:"value"`
	Tags      map[string]string `json:"tags"`
}

type responseExample struct {
	Message string `json:"message"`
	Error   string `json:"error"`
}

type TagvFilters struct {
	Type    string `json:"type"`
	Tagk    string `json:"tagk"`
	Filter  string `json:"filter"`
	GroupBy bool   `json:"groupBy"`
}

type TSSubQuery struct {
	Aggregator   string            `json:"aggregator"`
	Metric       string            `json:"metric"`
	Downsample   string            `json:"downsample"`
	Tags         map[string]string `json:"tags"`
	Filters      []TagvFilters     `json:"filters"`
	ExplicitTags bool              `json:"explicitTags"`
}

type TSQuery struct {
	//用来支持相对时间和绝对时间
	Start        interface{}  `json:"start"`
	End          interface{}  `json:"end"`
	Queries      []TSSubQuery `json:"queries"`
	MsResolution bool         `json:"msResolution"`
}

func (mux *MyMux) sayHello(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		http.Error(w, "the method is not allowed！", http.StatusMethodNotAllowed)
		return
	}
	_, err := fmt.Fprintf(w, "Hello World!")
	fmt.Printf("在控制台输出Hello,World!\n")
	if err != nil {
		http.Error(w, "控制台打印出错1", http.StatusExpectationFailed)
		return
	}
}

func (mux *MyMux) sayHi(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		http.Error(w, "the method is not allowed！", http.StatusMethodNotAllowed)
		return
	}
	_, err := fmt.Fprintf(w, "Hi World!")
	fmt.Printf("在控制台输出Hi,World!\n")
	if err != nil {
		http.Error(w, "控制台打印出错2", http.StatusExpectationFailed)
		return
	}
}

func (mux *MyMux) writeJsonToClient(w http.ResponseWriter, r *http.Request) {

	response := make([]*responseExample, 3)
	//初始化response的信息
	for index := range response {
		response[index] = &responseExample{
			Message: "this is test message " + strconv.Itoa(index),
			Error:   "this is test error " + strconv.Itoa(index),
		}
	}
	//解析go结构体变量为json数据
	jsonData, err := json.Marshal(response)
	if err != nil {
		log.Fatalf("JSON marshaling  failed: %s", err)
	}
	w.Header().Set("Content-Type", "application/json")
	//打印到控制台
	fmt.Printf("%s\n", jsonData)
	//打印到浏览器
	_, _ = fmt.Fprintf(w, "%s\n", jsonData)
}

func (mux *MyMux) parseJsonFromClient1(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Error(w, "the method is not allowed", http.StatusMethodNotAllowed)
	}

	if r.Header.Get("Content-TyPe") != "application/json" {
		http.Error(w, "please set Encode method application/json", http.StatusBadRequest)
	}

	//流式解码器
	br := bufio.NewReader(r.Body)

	// 查看前1个字节
	f, err := br.Peek(1)
	if err != nil || len(f) != 1 {
		http.Error(w, "peek error: "+err.Error(), http.StatusBadRequest)
		return
	}
	// Peek to see if this is a JSON array.
	var multi bool
	switch f[0] {
	case '{':
		multi = false
	case '[':
		multi = true
	default:
		http.Error(w, "expected JSON array or hash", http.StatusBadRequest)
		return
	}

	dps := make([]point, 1)
	//如果是多个对象{}组成的数组[],那么就解码到dps
	if dec := json.NewDecoder(br); multi {
		if err := dec.Decode(&dps); err != nil {
			http.Error(w, "json array decode error", http.StatusBadRequest)
			return
		}
	} else { //否则，解码到dps[0]
		if err = dec.Decode(&dps[0]); err != nil {
			http.Error(w, "json object decode error", http.StatusBadRequest)
			return
		}
	}
	// fmt.Print(len(dps))
	// fmt.Print(dps)
	jsonData, err := json.Marshal(dps)
	if err != nil {
		log.Fatalf("JSON marshaling  failed: %s", err)
	}

	w.Header().Set("Content-Type", "application/json")
	fmt.Printf("%s\n", jsonData)

	_, _ = fmt.Fprintf(w, "%s\n", jsonData)
	//_, _ = fmt.Fprint(w, jsonData)
}

func (mux *MyMux) parseJsonFromClient2(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Error(w, "the method is not allowed", http.StatusMethodNotAllowed)
	}

	//得到序列化的字节流
	result, _ := ioutil.ReadAll(r.Body)

	//将字节流反序列化成对象。将json数据格式转成对象格式
	var query TSQuery
	if err := json.Unmarshal(result, &query); err != nil {
		log.Fatalf("Json unmarshaling failed: %s", err)
	}

	end := reflect.TypeOf(query.End).String()
	if end == "float64" {
		endTime := query.End.(float64)
		fmt.Printf("浮点型结束时间是：%f", endTime)

		endTimeInt := int64(endTime)
		fmt.Printf("整型结束时间是：%d", endTimeInt)
	}

	//第一种方法判断类型
	fmt.Printf("%T", query.Start)
	fmt.Printf("%T", query.End)

	//第二种方法判断类型
	fmt.Print(reflect.TypeOf(query.End).String())
	fmt.Print(reflect.TypeOf(query.Start).String())

	//第三种方法判断类型
	fmt.Println(judgeType(query.Start))

	//取出转换之后对象里的值
	fmt.Printf("start=%d", query.Start)
	fmt.Print("\n")
	fmt.Printf("end=%d", query.End)
	fmt.Print("\n")
	fmt.Printf("msResolution=%t", query.MsResolution)
	fmt.Print("\n")
	returnString := fmt.Sprintf("%s-%d-%t-%T", r.URL.Path, query.Start, query.MsResolution, query.Queries)
	fmt.Println(returnString)

	//遍历slice. index 和 value
	for index, subQuery := range query.Queries {
		fmt.Printf("子查询的index=%d，aggregator=%s，metric=%s, downsample=%s, tags=%v, explicitTags=%t, filters=%v \n",
			index, subQuery.Aggregator, subQuery.Metric, subQuery.Downsample, subQuery.Tags, subQuery.ExplicitTags, subQuery.Filters)
		//遍历map. name 和 value
		for name, value := range subQuery.Tags {
			fmt.Printf("TagKey是%s，TagValue是%s\n", name, value)
		}

		for index1, filter := range subQuery.Filters {
			fmt.Printf("filter的index=%d, type=%s, tagk=%s, filter=%s, groupBy=%t \n", index1, filter.Type, filter.Tagk, filter.Filter, filter.GroupBy)
		}

		fmt.Print("\n\n")
	}

	//将刚刚转换之后的对象格式又序列化成字节流
	jsonData, _ := json.MarshalIndent(query, "", "	")
	fmt.Printf("%s\n", jsonData)
	//输出到浏览器端
	w.Header().Set("Content-Type", "application/json")
	_, _ = fmt.Fprintf(w, "%s\n", jsonData)

}

func judgeType(v interface{}) string {
	switch i := v.(type) {
	case int:
		return "int"
	case int64:
		return "int 64"
	case int32:
		return "int32"
	case string:
		return "string"
	case float64:
		return "float64"
	default:
		_ = i
		return "unknown"
	}
}

func (mux *MyMux) parseJsonFromClient3(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Error(w, "the method is not allowed", http.StatusMethodNotAllowed)
	}

	//得到序列化的字节流
	content, _ := ioutil.ReadAll(r.Body)
	var results []point
	if err := json.Unmarshal(content, &results); err != nil {
		fmt.Println(err.Error())
	}

	//将刚刚转换之后的对象格式又序列化成字节流
	jsonData, _ := json.MarshalIndent(results, "", "	")
	fmt.Printf("%s\n", jsonData)
	//输出到浏览器端
	w.Header().Set("Content-Type", "application/json")
	_, _ = fmt.Fprintf(w, "%s\n", jsonData)
}

func (mux *MyMux) parseGetParameter(w http.ResponseWriter, r *http.Request) {

	if r.Method != "GET" {
		http.Error(w, "the method is not allowed", http.StatusMethodNotAllowed)
	}

	fmt.Println("r.URL.RawQuery: ", r.URL.RawQuery)
	fmt.Println("r.URL.Path: ", r.URL.Path)
	fmt.Println("r.URL.Host: ", r.URL.Host)
	fmt.Println("r.URL.Fragment: ", r.URL.Fragment)
	fmt.Println("r.URL.Opaque: ", r.URL.Opaque)
	fmt.Println("r.URL.RawPath: ", r.URL.RawPath)
	fmt.Println("r.URL.Scheme: ", r.URL.Scheme)
	fmt.Println()
	fmt.Println("r.URL.RequestURI(): ", r.URL.RequestURI())
	fmt.Println("r.URL.Hostname(): ", r.URL.Hostname())
	fmt.Println("r.URL.Port(): ", r.URL.Port())
	fmt.Println("r.URL.String(): ", r.URL.String())
	fmt.Println("r.URL.EscapedPath(): ", r.URL.EscapedPath())
	fmt.Println()
	fmt.Println("r.Host: ", r.Host)
	fmt.Println("r.Method: ", r.Method)
	fmt.Println("r.UserAgent(): ", r.UserAgent())
	fmt.Println("r.RequestURI: ", r.RequestURI)
	fmt.Println("r.RemoteAddr: ", r.RemoteAddr)
	fmt.Println("r.FormValue(start): ", r.FormValue("start"))
	fmt.Println("r.FormValue(end): ", r.FormValue("end"))
	fmt.Println("r.FormValue(m): ", r.FormValue("m"))
	fmt.Println("r.FormValue(ms): ", r.FormValue("ms"))
	fmt.Println()
	fmt.Println("r.URL.Query().Get(start): ", r.URL.Query().Get("start"))
	fmt.Println("r.URL.Query().Get(end):", r.URL.Query().Get("end"))
	fmt.Println("r.URL.Query().Get(m):", r.URL.Query().Get("m"))
	fmt.Println("r.r.URL.Query()", r.URL.Query())
	fmt.Println("r.r.URL.Query()[“”\"m\"]", r.URL.Query()["m"])
	fmt.Println("r.URL.Query().Get(ms):", r.URL.Query().Get("ms"))

	fmt.Println("r.Header.Get(Content-Type):", r.Header.Get("Content-Type"))

}

//实现http.Handler这个接口的唯一方法
func (mux *MyMux) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	urlPath := r.URL.Path
	switch urlPath {
	case "/hello":
		mux.sayHello(w, r)
	case "/hi":
		mux.sayHi(w, r)
	case "/jsonGet":
		mux.writeJsonToClient(w, r)
	case "/jsonParse1":
		mux.parseJsonFromClient1(w, r)
	case "/jsonParse2":
		mux.parseJsonFromClient2(w, r)
	case "/jsonParse3":
		mux.parseJsonFromClient3(w, r)
	case "/api/v1/getTest":
		mux.parseGetParameter(w, r)
	default:
		http.Error(w, "没有此url路径", http.StatusBadRequest)
	}
}

func main() {
	//实例化路由器Handler
	mymux := &MyMux{}
	//基于TCP服务监听8088端口
	ln, err := net.Listen("tcp", ":8087")
	if err != nil {
		fmt.Printf("设置监听端口出错...")
	}
	//调用http.Serve(l net.Listener, handler Handler)方法，启动监听
	err1 := http.Serve(ln, mymux)
	if err1 != nil {
		fmt.Printf("启动监听出错")
	}
}
