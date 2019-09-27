package com.github.mrkinghh.datastruct.sort.exchangesort;

public class BubbleSort {

  public static void bubbleSort(int[] arr) {
    int len = arr.length;
    //外层循环控制循环次数
    for (int i = 1; i <= len - 1; i++) {
      //内层控制每次的交换
      for (int j = 0; j < len - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          int temp = 0;
          temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
  }

}
