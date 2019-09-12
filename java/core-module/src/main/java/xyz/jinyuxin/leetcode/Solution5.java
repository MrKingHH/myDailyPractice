package xyz.jinyuxin.leetcode;

public class Solution5 {
  public static void main(String[] args) {
    String s = "jhgtrclvzumufurdemsogfkpzcwgyepdwucnxrsubrxadnenhvjyglxnhowncsubvdtftccomjufwhjupcuuvelblcdnuchuppqpcujernplvmombpdttfjowcujvxknzbwmdedjydxvwykbbamfnsyzcozlixdgoliddoejurusnrcdbqkfdxsoxxzlhgyiprujvvwgqlzredkwahexewlnvqcwfyahjpeiucnhsdhnxtgizgpqphunlgikogmsffexaeftzhblpdxrxgsmeascmqngmwbotycbjmwrngemxpfakrwcdndanouyhnnrygvntrhcuxgvpgjafijlrewfhqrguwhdepwlxvrakyqgstoyruyzohlvvdhvqmzdsnbtlwctetwyrhhktkhhobsojiyuydknvtxmjewvssegrtmshxuvzcbrabntjqulxkjazrsgbpqnrsxqflvbvzywzetrmoydodrrhnhdzlajzvnkrcylkfmsdode";
    System.out.println(longestPalindrome(s));
  }

  public static String longestPalindrome(String s) {
    if (s.isEmpty()) {
      return s;
    }

    int len = s.length();
    //从长度为len开始判断是不是回文
    for (int i = len; i > 0; i--) {
      for (int j = 0; j <= len - i; j++) {
        String subStr = s.substring(j, j + i);
        if (isHuiWen(subStr)) {
          return subStr;
        }
      }
    }
    return null;
  }

  public static boolean isHuiWen(String s) {
    int len = s.length();
    if (s.length() == 1) {
      return true;
    }

    boolean flag = true;
    for (int i = 0; i < len; i++) {
      if (s.charAt(i) != s.charAt(len - i - 1)) {
        flag = false;
      }
    }

    return flag;
  }
}
