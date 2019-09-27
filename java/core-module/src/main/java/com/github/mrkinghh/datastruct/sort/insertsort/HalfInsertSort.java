package com.github.mrkinghh.datastruct.sort.insertsort;

public class HalfInsertSort {

  public static void halfInsertSort(int[] arr) {
    int i;
    int j;
    int low;
    int high;
    int mid;
    int len = arr.length;
    //从第二个元素开始往前插入
    for (i = 2; i < len; i++) {
      //用0号单元做哨兵
      arr[0] = arr[i];
      low = 1;
      high = i - 1;
      //查找序号i之前的有序子表
      //退出循环的时候，必然是low大于high。
      //退出前肯定是low=high=mid
      //如果有序列表中已经存在这个数那么退出前high=mid-1，此时low=mid>high(根据下面的条件)
      //如果value比退出循环前的数要大，表示要插入的数在mid的右边，此时low=mid+1，此时low>mid=high，在high后面插入即可
      //如果value比退出循环前的数要小，表示要找的数在左mid的左边，此时high=mid-1，此时low=mid>high，在high后面插入即可
      while (low <= high) {
        mid = (low + high) / 2;
        if (arr[0] > arr[mid]) {
          low = mid + 1;
        } else {
          high = mid - 1;
        }
      }
      //从i-1的到找到的位置，依次往后移动元素，
      for (j = i - 1; j >= high + 1; j--) {
        arr[j + 1] = arr[j];
      }

      arr[high + 1] = arr[0];
    }
  }
}
