package xyz.jinyuxin.datastruct.sort.exchangesort;

public class QuickSort {
  public static void main(String[] args) {
    int[] arr = {8,5,6,7,4,2,3,1};
    int len = arr.length;
    quickSort(arr, 0,len-1);
    for(int i:arr) {
      System.out.println(i);
    }
  }

  public static void quickSort(int[] arr, int low, int high) {
    if(low < high) {
      int pos = findPosition(arr, low, high);
      //递归对右半边进行快排
      quickSort(arr,low,pos-1);
      //对左半边进行快排
      quickSort(arr,pos+1,high);
    }
  }

  public static int findPosition(int[] arr, int low, int high) {
    //枢纽值
    int temp = arr[low];

    //low等于high的时候退出循环
    while(low < high) {
      //从后向前，直到找到第一个小于temp的数才退出
      while(low< high && arr[high] >= temp){
        high--;
      }
      //找到小于枢纽值的数以后
      arr[low] = arr[high];

      //从前向后，直到找到第一个大于temp的数才退出
      while(low < high &&arr[low] <= temp) {
        low++;
      }
      //找到大于枢纽值的数以后
      arr[high] = arr[low];
    }

    //此时将枢纽值赋值给low=high位置
    arr[low] = temp;
    //返回枢纽的最终位置
    return low;
  }
}
