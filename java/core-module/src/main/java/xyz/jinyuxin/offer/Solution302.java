package xyz.jinyuxin.offer;


/**
 * 面试题3.2 不修改数组找出重复的数字
 */
public class Solution302 {
  public static void findDuplicate(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }
    int len = arr.length;
    int start = 1;
    int end = len - 1;
    while (end >= start) {

      int middle = (start + end) / 2;

      //统计范围内的数字个数
      int num = countRange(arr, start, middle);
      //如果此时头指针和尾指针指向同一个位置
      if (start == end) {
        if (num > 1) {
          System.out.printf("找到一个重复数字%d\n", start);
          return;
        } else {
          break;
        }
      }

      //如果位于start-middle之间的数字个数大于start-middle+1,
      //说明在此区间有重复数字，需要调整尾指针的位置
      if (num > (middle - start + 1)) {
        end = middle;
      } else { //如果在左半部分没有，那么肯定在右半部分有重复的，调整头指针的位置
        start = middle + 1;
      }
    }
    System.out.println("没有找到重复数字...");
  }

  public static int countRange(int[] arr, int start, int end) {
    if (arr == null || arr.length == 0) {
      return -1;
    }
    int count = 0;
    int len = arr.length;
    for (int i = 0; i < len; i++) {
      //统计在start-end范围内出现的次数
      if (arr[i] >= start && arr[i] <= end) {
        count++;
      }
    }
    return count;
  }
}
