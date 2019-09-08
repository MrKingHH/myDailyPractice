package main

import (
	"encoding/json"
	"fmt"
	"net"
	"net/http"
)

type JsonTest struct {
	Test1 string   `json:"test1"`
	Test2 []string `json:"test2"`
}

var array = make([]string, 1)

//定义自己的路由器
type MyMux1 struct {
}

//实现http.Handler这个接口的唯一方法
func (mux *MyMux1) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	urlPath := r.URL.Path
	switch urlPath {
	case "/test":
		mux.testJson(w, r)
	case "/test1":
		mux.testJson1(w, r)
	default:
		http.Error(w, "没有此url路径", http.StatusBadRequest)
	}
}

func (mux *MyMux1) testJson(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		http.Error(w, "the method is not allowed", http.StatusMethodNotAllowed)
	}

	jsontest := &JsonTest{}

	jsontest.Test1 = "value1"
	jsontest.Test2 = make([]string, 0)

	jsondata, _ := json.Marshal(jsontest)
	w.Header().Set("Content-Type", "application/json")
	fmt.Fprintf(w, "%s", jsondata)
}

func (mux *MyMux1) testJson1(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		http.Error(w, "the method is not allowed", http.StatusMethodNotAllowed)
	}

	jsontest := make([]string, 0)

	jsontest = append(jsontest, "abc")
	jsontest = append(jsontest, "def")

	jsondata, _ := json.Marshal(jsontest)
	w.Header().Set("Content-Type", "application/json")
	fmt.Fprintf(w, "%s", jsondata)
}

func main() {
	//实例化路由器Handler
	mymux := &MyMux1{}
	//基于TCP服务监听8088端口
	ln, err := net.Listen("tcp", ":8089")
	if err != nil {
		fmt.Printf("设置监听端口出错...")
	}
	//调用http.Serve(l net.Listener, handler Handler)方法，启动监听
	err1 := http.Serve(ln, mymux)
	if err1 != nil {
		fmt.Printf("启动监听出错")
	}
}
