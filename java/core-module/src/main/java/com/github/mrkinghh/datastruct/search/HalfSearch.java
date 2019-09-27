package com.github.mrkinghh.datastruct.search;

public class HalfSearch {

  public static int halfSearch(int[] arr, int value) {
    int len = arr.length;
    int low = 0;
    int high = len - 1;
    int mid;
    //退出循环的时候，必然是low大于high。
    //没找到退出前肯定是low=high=mid
    //如果value比退出循环前的数要大，表示要找的数在右边，此时low=mid+1，此时low>mid=high
    //如果value比退出循环前的数要小，表示要找的数在左边，此时high=mid-1，此时low=mid>high
    while (low <= high) {
      mid = (low + high) / 2;
      if (value == arr[mid]) {
        return mid;
      }
      //在右侧找
      if (value > arr[mid]) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    return -1;
  }

}
