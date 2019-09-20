package xyz.jinyuxin.findjob.hw.a20190911;

/**
 * 对数字、字符、字符串，以及数字与字符串组合进行倒排列
 * 输入：I am an 20-years out--standing @ * -stu dent
 * 输出：dent stu standing out 20-years an am I
 */
public class Solution2 {
  public static void printReverseString(String line) {
    //两个--替换成一个空格
    String str = line.replaceAll("--", " ");
    //列出所有特殊字符,把所有特殊字符替换成一个空格
    String regex = "[`~!@#$%^&*()+=|{}':;\\[\\].<>/·！￥…（）—【】；：“”‘’。，、？]";
    String str1 = str.replaceAll(regex, " ");

    //将出现了两次及以上的空格替换成为一个空格
    String str2 = str1.replaceAll("\\s{2,}", " ");
    //去掉前后空格再切分
    String[] strArr = str2.trim().split(" ");
    int len = strArr.length;
    for (int i = len - 1; i >= 0; i--) {
      //如果前面或者后面是-
      if (strArr[i].startsWith("-") || strArr[i].endsWith("-")) {
        if (strArr[i].startsWith("-") && !strArr[i].endsWith(("-"))) {
          System.out.print(strArr[i].substring(1) + " ");
        } else if (!strArr[i].startsWith("-") && strArr[i].endsWith(("-"))) {
          System.out.print(strArr[i].substring(0, strArr[i].length() - 1) + " ");
        } else {
          System.out.print(strArr[i].substring(1, strArr[i].length() - 1) + " ");
        }
      } else {
        System.out.print(strArr[i] + " ");
      }
    }

  }
}
