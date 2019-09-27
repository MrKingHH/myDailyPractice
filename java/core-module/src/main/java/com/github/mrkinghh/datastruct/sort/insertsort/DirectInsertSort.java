package com.github.mrkinghh.datastruct.sort.insertsort;

public class DirectInsertSort {

  //用了第0个元素作为哨兵，所以第0个元素不参与排序
  public static void insertSort(int[] arr) {
    int len = arr.length;
    int i;
    int j;
    if (len == 1) {
      return;
    }
    //从第二个元素开始往前插入，arr[0]作为哨兵，不参与排序
    for (i = 2; i < len; i++) {
      //如果当前位置的数比前面的数小，则找到插入位置,插入该位置
      if (arr[i] < arr[i - 1]) {
        //暂存当前位置的数
        arr[0] = arr[i];
        //从后往前找插入位置,边比较边移动
        for (j = i - 1; arr[0] < arr[j]; j--) {
          //赋值运算符从右至左
          arr[j + 1] = arr[j];
        }
        //for循环退出时，arr[0]>=arr[j],故arr[0]应该插入arr[j]的下一个位置，相对位置不会变，即使相等也是在后面
        arr[j + 1] = arr[0];
      }
    }
  }


  //不用哨兵，所有元素排序
  /**
   * 这种不用哨兵的方法时间和空间的效率太低
   * 每次循环都要多一次逻辑与的判断
   * 还借助临时变量
   */
  public static void insertSort1(int[] arr) {
    int len = arr.length;
    int i;
    int j;
    if (len == 1) {
      return;
    }

    //从第二个元素开始排序
    for (i = 1; i < len; i++) {
      if (arr[i] < arr[i - 1]) {
        int temp = arr[i];
        //j=0判断完以后，j--成为-1
        for (j = i - 1; j >= 0 && temp < arr[j]; j--) {
          arr[j + 1] = arr[j];
        }
        arr[j + 1] = temp;
      }
    }
  }

}
