package xyz.jinyuxin.leetcode;

public class Solution859 {

  /**
   * 859.亲密字符串
   * 解题思路:
   * (1)如果两个字符内容完全一样：比如都是aaaa或者abab
   * 需要判断a中有没有出现相同的字符，如果有的话，那么就能交换这两个相同字符得到b.
   * (2)如果两个字符串内容不一样，判读不同字符个数，个数不等于2直接返回false.
   * 否则我们需要记住不一样的两个位置，判断两个不同位置的值是否相等。
   */
  public static boolean buddyStrings(String a, String b) {
    if (a == null || b == null || a.isEmpty() || b.isEmpty()) {
      return false;
    }

    if (a.length() != b.length()) {
      return false;
    }

    boolean flag = false;
    int len = a.length();
    if (a.equals(b)) {
      //abab abab aaaa aaaa怎么处理
      //找到A中重复的字符 交换两个重复的字符 肯定能得到B
      for (int i = 0; i < len; i++) {
        //不存在最后一个的话，说明只有一个字符
        char c = a.charAt(i);
        int lastIndex = b.lastIndexOf(c);
        //如果返回的下标等于当前字符的下标的话，那说明只有一个字符
        if (lastIndex == i) {
          continue;
        } else {
          flag = true;
        }
      }
    } else {
      //
      int count = 0;
      for (int i = 0; i < len; i++) {
        if (a.charAt(i) != b.charAt(i)) {
          count++;
        }
      }
      if (count != 2) {
        return false;
      }
      //indexArr用于记录两个不相同的位置
      int[] indexArr = new int[3];
      int index = 0;
      for (int i = 0; i < len; i++) {
        if (a.charAt(i) != b.charAt(i)) {
          indexArr[index++] = i;
        }
      }

      flag = a.charAt(indexArr[0]) == b.charAt(indexArr[1])
              && a.charAt(indexArr[1]) == b.charAt(indexArr[0]);
    }

    return flag;
  }

}
