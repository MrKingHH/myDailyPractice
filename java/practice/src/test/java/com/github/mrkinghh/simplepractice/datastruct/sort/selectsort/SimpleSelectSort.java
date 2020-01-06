package com.github.mrkinghh.simplepractice.datastruct.sort.selectsort;

public class SimpleSelectSort {

  public static void main(String[] args) {
    int[] arr = {-1, 6, 5, 7, 4, 3, 2, 1};
    simpleSelectSort(arr);
    for (int x : arr) {
      System.out.println(x);
    }
  }

  public static void simpleSelectSort(int[] arr) {
    int len = arr.length;
    int min;
    //进行n-1趟排序
    for (int i = 0; i < len - 1; i++) {
      //假设每趟排序最小的元素为第一个，所以为i
      min = i;
      //在后面的i+1->n中间选一个最小的
      for (int j = i + 1; j < len; j++) {
        if (arr[j] < arr[i]) {
          min = j;
        }
      }
      //如果后面有比当前位置元素小的，就交换
      //如果没有，那么就说明本身是最小的
      if (min != i) {
        int temp = 0;
        temp = arr[min];
        arr[min] = arr[i];
        arr[i] = temp;
      }
    }
  }
}

