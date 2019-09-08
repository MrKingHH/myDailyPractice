package main

import (
	"fmt"
	"math/rand"
	"os"
	"time"
)

func WriteToFile() {

	timestamp := 1559318400
	metric := [...]string{1: "cpu.usage.test1", 2: "cpu.usage.test2", 3: "sys.memory.test1", 4: "sys.memory.test2", 5: "net.flow.test1", 6: "net.flow.test2"}
	host := [...]string{1: "a01", 2: "a02", 3: "a03", 4: "b01", 5: "b02", 6: "b03"}
	location := [...]string{1: "a701", 2: "a702", 3: "a703", 4: "b601", 5: "b602", 6: "b603"}
	brand := [...]string{1: "lenovo"}

	//第二个参数指定了文件操作的模式，从左到右依次是 只写文件 没有就创建 追加形式 打开前清空内容 Filemode代表文件在linux中的权限
	f, err := os.OpenFile("./test.json", os.O_WRONLY|os.O_CREATE|os.O_APPEND|os.O_TRUNC, 666)
	check(err)
	//设置种子数
	//生成随机数之前要生成一个种子，如果不设种子，那么在一次编译过后，每次随机的数都一样
	rand.Seed(time.Now().UnixNano())
	_, _ = fmt.Fprintf(f, "[\n")
	for i := 1; i <= 9999; i++ {
		fmt.Fprintf(f, "{\n")
		fmt.Fprintf(f, "\t\"metric\":\"%s\",\n", metric[rand.Intn(6)+1])
		fmt.Fprintf(f, "\t\"timestamp\":%d,\n", timestamp)
		fmt.Fprintf(f, "\t\"value\":%d,\n", rand.Intn(500))
		fmt.Fprintf(f, "\t\"tags\":{\n")
		fmt.Fprintf(f, "\t\t\"host\":\"%s\",\n", host[rand.Intn(6)+1])
		fmt.Fprintf(f, "\t\t\"location\":\"%s\",\n", location[rand.Intn(6)+1])
		fmt.Fprintf(f, "\t\t\"brand\":\"%s\"\n", brand[1])
		fmt.Fprintf(f, "\t}\n")
		fmt.Fprintf(f, "},\n")
		timestamp = timestamp + 1
	}
	//第1w条，最后一个没有逗号
	fmt.Fprintf(f, "{\n")
	fmt.Fprintf(f, "\t\"metric\":\"%s\",\n", metric[rand.Intn(6)+1])
	fmt.Fprintf(f, "\t\"timestamp\":%d,\n", timestamp)
	fmt.Fprintf(f, "\t\"value\":%d,\n", rand.Intn(500))
	fmt.Fprintf(f, "\t\"tags\":{\n")
	fmt.Fprintf(f, "\t\t\"host\":\"%s\",\n", host[rand.Intn(6)+1])
	fmt.Fprintf(f, "\t\t\"location\":\"%s\",\n", location[rand.Intn(6)+1])
	fmt.Fprintf(f, "\t\t\"brand\":\"%s\"\n", brand[1])
	fmt.Fprintf(f, "\t}\n")
	fmt.Fprintf(f, "}\n")
	_, _ = fmt.Fprintf(f, "]")
}

func check(err error) {
	if err != nil {
		panic(err)
	}
}

func main() {
	WriteToFile()
}
