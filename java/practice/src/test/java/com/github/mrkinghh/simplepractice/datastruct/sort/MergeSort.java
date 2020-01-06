package com.github.mrkinghh.simplepractice.datastruct.sort;

public class MergeSort {

  public static void mergeSort(int[] arr, int low, int high) {
    if (low < high) {
      int mid = (low + high) / 2;
      mergeSort(arr, low, mid);
      mergeSort(arr, mid + 1, high);
      merge(arr, low, mid, high);
    }
  }

  //合并两个有序的数组中的部分
  public static void merge(int[] arr, int low, int mid, int high) {
    //辅助数组，用来存放合并的结果
    int[] temp = new int[high - low + 1];
    //把数组arr的元素拷贝到temp中
    for (int k = low; k <= high; k++) {
      temp[k - low] = arr[k];
    }
    int i;
    int j;
    int k;
    for (i = low, j = mid + 1, k = i; i <= mid && j <= high; k++) {
      if (temp[i - low] <= temp[j - low]) {
        arr[k] = temp[i - low];
        i++;
      } else {
        arr[k] = temp[j - low];
        j++;
      }
    }

    while (i <= mid) {
      arr[k++] = temp[i - low];
      i++;
    }

    while (j <= high) {
      arr[k++] = temp[j - low];
      j++;
    }
  }

}
