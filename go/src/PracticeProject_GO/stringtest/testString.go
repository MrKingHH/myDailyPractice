package main

import (
	"fmt"
	"strings"
	"unicode/utf8"
)

func main() {

	filterValue := "regexp(sjc.*)"
	length := len(filterValue)
	leftBucketIndex := strings.Index(filterValue, "(")
	fmt.Println(filterValue[:leftBucketIndex])
	fmt.Println(filterValue[leftBucketIndex+1 : length-1])
	//纯英文
	testString1 := "China!"
	length1 := len(testString1)
	fmt.Printf("testString1字符串的长度是：%d\n", length1)
	lastCharA := testString1[length1-1]
	//此处用%s格式输出最后一个字符会出错，只能用%c
	fmt.Printf("testString1字符串中最后一个字符是：%s\n", lastCharA)
	fmt.Printf("testString1字符串中最后一个字符是：%c\n", lastCharA)
	fmt.Printf("testString1中的下标0-2的子字符串是:%s\n", testString1[0:3])
	fmt.Printf("testString1中的下标3-末尾的子字符串是:%s\n", testString1[3:])
	fmt.Println()

	//中英文加一起15个字符
	testString2 := "我爱你中国,我爱你China!"
	//此处长度是输出字节数，Go语言中文字符是UTF-8编码，长度3字节，故此处应该是15+1+9+6=31
	length2 := len(testString2)
	fmt.Printf("testString2字符串的长度是：%d\n", length2)
	fmt.Printf("testString2中的最后一个字符是：%s\n", testString2[length2-1])
	fmt.Printf("testString2中的最后一个字符是：%c\n", testString2[length2-1])
	fmt.Printf("testString2中的下标6-末尾的子字符串是:%s\n", testString2[:15])
	fmt.Printf("testString2中的下标6-末尾的子字符串是:%s\n", testString2[:16])
	fmt.Println()

	//此处就是统计字符数
	length3 := utf8.RuneCountInString(testString2)
	fmt.Printf("使用utf8中的方法统计的字符串长度是：%d\n", length3)

	fmt.Println()

	//转成rune类型，再统计字符数
	temp := []rune(testString2)
	//获取中英文混合字符串长度
	length4 := len(temp)
	fmt.Printf("使用rune统计的字符串的长度是：%d\n", length4)
	//获取字符串中最后一个字符
	lastCharB := string(temp[length4-1])
	commaIndex1 := strings.Index(testString2, ",")
	fmt.Printf("逗号下标为：%d\n", commaIndex1)

	commaIndex2 := strings.Index(string(temp), ",")
	fmt.Printf("逗号下标为：%d\n", commaIndex2)

	//获取下标从0到3(不包括3)的子串
	subString1 := temp[0:3]
	subString2 := temp[6:]
	fmt.Printf("testString2中的最后一个字符是：%s\n", lastCharB)
	fmt.Printf("testString2中的下标0-2的子字符串是:%s\n", string(subString1))
	fmt.Printf("testString2中的下标6-末尾的子字符串是:%s\n", string(subString2))

}
