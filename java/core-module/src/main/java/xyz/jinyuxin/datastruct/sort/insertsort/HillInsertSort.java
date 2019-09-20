package xyz.jinyuxin.datastruct.sort.insertsort;

public class HillInsertSort {

  public static void hillInsertSort(int[] arr) {
    int len = arr.length;
    int dk;
    int i;
    int j;
    //控制步长，步长慢慢变小，最后等于1
    for (dk = len >> 1; dk >= 1; dk = dk >> 1) {
      //从1+dk之后每次逐步移动1位
      for (i = 1 + dk; i < len; i++) {
        arr[0] = arr[i];
        for (j = i - dk; j > 0 && arr[0] < arr[j]; j = j - dk) {
          arr[j + dk] = arr[j];
        }
        arr[j + dk] = arr[0];
      }
    }
  }

}
