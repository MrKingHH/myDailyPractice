package main

import (
	"github.com/thedevsaddam/gojsonq"
	"golang.org/x/exp/errors/fmt"
	"log"
)

func main() {
	jq := gojsonq.New().File("./gojsonq/sample-data.json")
	//res := jq.Find("vendor.items.[1].name")
	//if jq.Error() != nil {
	//	log.Fatal(jq.Error())
	//}
	//fmt.Print(res)

	//res1 := jq.Find("select * from vendor.items where price > 1200 or id null")
	//if jq.Error() != nil {
	//	log.Fatal(jq.Error())
	//}
	//fmt.Print(res1)

	res1 := jq.From("vendor.items").Where("price", ">", 1200).OrWhere("id", "=", nil).
		Only("name", "price")

	if jq.Error() != nil {
		log.Fatal(jq.Error())
	}

	fmt.Println(res1)

}
