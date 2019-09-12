package xyz.jinyuxin.datastruct.sort.exchangesort;

public class BubbleSort {
  public static void main(String[] args) {
    int[] arr = {8, 6, 5, 7, 4, 2, 3, 1};
    BubbleSort.bubbleSort(arr);
    for (int i : arr) {
      System.out.println(i);
    }
    double x = 3.0/9;
    float y = 3.0f/9;
    //double精确到16位
    System.out.println(x);
    //float精确到8位
    System.out.println(y);
  }

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
