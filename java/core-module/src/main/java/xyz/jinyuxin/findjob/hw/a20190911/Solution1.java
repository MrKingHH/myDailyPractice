package xyz.jinyuxin.findjob.hw.a20190911;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * 1. ai<=bj
 * 2. ai bj 距离小于等于r 找不到的话就返回最近的那个,没有最近的，就丢弃ai。
 * 输入:A={1,3,5},B={2,4,6},R=1
 * 输出：{1,2}{3,4}{5,6}
 * */
public class Solution1 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String line = sc.nextLine();
    List<String> list = getString(line);
    printPair(list);
  }

  //1,2,3 4,5,6 1
  public static void printPair(List<String> list) {
    //1 2 3
    String[] strArr1 = list.get(0).split(",");
    //4 5 6
    String[] strArr2 = list.get(1).split(",");
    int len1 = strArr1.length;
    int len2 = strArr2.length;
    int[] intArr1 = new int[len1];
    int[] intArr2 = new int[len2];
    for (int i = 0; i < len1; i++) {
      intArr1[i] = Integer.parseInt(strArr1[i]);
    }

    for (int i = 0; i < len2; i++) {
      intArr2[i] = Integer.parseInt(strArr2[i]);
    }

    int distance = Integer.parseInt(list.get(2));
    //记录当前的ai对应的次数
    int[] times = new int[len1];
    for (int i = 0; i < len1; i++) {
      for (int j = 0; j < len2; j++) {
        if (intArr1[i] <= intArr2[j]) {
          //如果当前的bj与ai距离小于r,那么就输出，继续找下一个bj
          if (Math.abs(intArr1[i] - intArr2[j]) <= distance) {
            times[i]++;
            System.out.print("(" + intArr1[i] + "," + intArr2[j] + ")");
          } else {
            //在距离大于distance的时候，要保证ai在前面没有找到距离小于等于distance的数，即times[i]等于0
           if (times[i] == 0) {
              System.out.print("(" + intArr1[i] + "," + intArr2[j] + ")");
            }
          }
        }
      }
    }

  }


  //得到 A B R 三部分的字符串
  public static List<String> getString(String line) {
    List<String> list = new ArrayList<>();
    String[] strArr = line.split("=");
    String aSection = strArr[1];
    String bSection = strArr[2];
    //1,3,5
    String aNumber = aSection.substring(1, aSection.length() - 3);
    //2,4,6
    String bNumber = bSection.substring(1, bSection.length() - 3);
    list.add(aNumber);
    list.add(bNumber);
    //最后一个是R的值 1
    list.add(strArr[strArr.length - 1]);
    return list;
  }


}
