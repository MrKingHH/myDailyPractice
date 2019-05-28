package main

import (
	"fmt"
	"net"
	"net/http"
)

//定义自己的路由器
type MyMux struct{

}

func (mux *MyMux) sayHello(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		http.Error(w, "the method is not allowed！", http.StatusMethodNotAllowed)
		return
	}
	_, err:=  fmt.Fprintf(w, "Hello World!")
	fmt.Printf("在控制台输出Hello,World!\n")
	if err != nil{
		http.Error(w, "控制台打印出错1", http.StatusExpectationFailed)
		return
	}
}

func (mux *MyMux) sayHi(w http.ResponseWriter, r *http.Request){
	if r.Method != "GET"{
		http.Error(w, "the method is not allowed！", http.StatusMethodNotAllowed)
		return
	}
	_, err:=  fmt.Fprintf(w, "Hi World!")
	fmt.Printf("在控制台输出Hi,World!\n")
	if err != nil{
		http.Error(w, "控制台打印出错2", http.StatusExpectationFailed)
		return
	}
}

//实现http.Handler这个接口的唯一方法
func (mux *MyMux) ServeHTTP(w http.ResponseWriter, r *http.Request){
	urlPath := r.URL.Path
	switch urlPath{
	case "/hello":
		mux.sayHello(w, r)
	case "/hi":
		mux.sayHi(w, r)
	default:
		http.Error(w, "没有此url路径", http.StatusBadRequest)
	}
}

func main(){
	//实例化路由器Handler
	mymux := &MyMux{}
	//基于TCP服务监听8088端口
	ln, err := net.Listen("tcp", ":8088")
	if err != nil{
		fmt.Printf("设置监听端口出错...")
	}
	//调用http.Serve(l net.Listener, handler Handler)方法，启动监听
	err1 := http.Serve(ln, mymux)
	if err1 != nil{
		fmt.Printf("启动监听出错")
	}
}