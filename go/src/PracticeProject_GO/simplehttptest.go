package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net"
	"net/http"
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

type TSSubQuery struct {
	Aggregator string `json:"aggregator"`
	Metric     string `json:"metric"`
	//此处tags不确定，因此不能定义具体的结构体
	Tags map[string]string `json:"tags"`
}

//简单的查询示例
type TSQuery struct {
	Start   int64        `json:"start"`
	End     int64        `json:"end"`
	Queries []TSSubQuery `json:"queries"`
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
	//解析结构体变量为json数据
	jsonData, err := json.Marshal(response)
	if err != nil {
		log.Fatalf("JSON marshaling  failed: %s", err)
	}

	//在控制台输出
	fmt.Printf("%s\n", jsonData)
	//在控制台输出
	w.Header().Set("Content-Type", "application/json")
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
	//如果是多个对象{}组成的数组[],那么久解码到dps
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
	//解析结构体变量为json数据
	jsonData, err := json.Marshal(dps)
	if err != nil {
		log.Fatalf("JSON marshaling  failed: %s", err)
	}

	//在控制台输出
	fmt.Printf("%s\n", jsonData)
	//在客户端输出
	w.Header().Set("Content-Type", "application/json")
	_, _ = fmt.Fprintf(w, "%s\n", jsonData)
}

func (mux *MyMux) parseJsonFromClient2(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Error(w, "the method is not allowed", http.StatusMethodNotAllowed)
	}

	result, _ := ioutil.ReadAll(r.Body)
	//将json数据格式转成对象格式
	var query TSQuery
	if err := json.Unmarshal(result, &query); err != nil {
		log.Fatalf("Json unmarshaling failed: %s", err)
	}

	//取出转换之后对象里的值
	fmt.Print(query.Start)
	fmt.Print("\n")
	fmt.Print(query.End)
	fmt.Print("\n")
	//遍历slice. index 和 value
	for index, subQuery := range query.Queries {
		fmt.Printf("索引是%d，聚合器是%s，Metric名字是%s \n", index, subQuery.Aggregator, subQuery.Metric)
		//遍历map. name 和 value
		for name, value := range subQuery.Tags {
			fmt.Printf("TagKey是%s，TagValue是%s\n", name, value)
		}
	}

	//将刚刚转换之后的对象格式又转成json格式
	jsonData, _ := json.MarshalIndent(query, "", "	")
	//在控制台输出
	fmt.Printf("%s\n", jsonData)
	//在客户端输出
	w.Header().Set("Content-Type", "application/json")
	_, _ = fmt.Fprintf(w, "%s\n", jsonData)
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
	default:
		http.Error(w, "没有此url路径", http.StatusBadRequest)
	}
}

func main() {
	//实例化路由器Handler
	mymux := &MyMux{}
	//基于TCP服务监听8088端口
	ln, err := net.Listen("tcp", ":8088")
	if err != nil {
		fmt.Printf("设置监听端口出错...")
	}
	//调用http.Serve(l net.Listener, handler Handler)方法，启动监听
	err1 := http.Serve(ln, mymux)
	if err1 != nil {
		fmt.Printf("启动监听出错")
	}
}
