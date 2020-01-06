package com.github.mrkinghh.simplepractice.datastruct.sort.selectsort;

public class HeapSort {

  public static void main(String[] args) {
    int[] arr = {-1, 8, 6, 5, 7, 4, 9, 11, 12, 14, 78, -9, -7, -10, 112, 99, 1024, 1111, 7890};
    heapSort(arr);
    for (int x : arr) {
      System.out.println(x);
    }
  }

  public static void heapSort(int[] arr) {
    //构建初始堆,此时堆顶元素是最大值
    buildMaxHeap(arr);
    int len = arr.length;
    //n-1次的调整工作
    for (int i = len; i > 1; i--) {
      //将待排序的最后一个元素和堆顶元素arr[1]交换
      int temp = arr[1];
      arr[1] = arr[i - 1];
      arr[i - 1] = temp;
      //调整剩下的堆，每次都从1开始调整，每次调整，堆长度减一
      adjustHeap(arr, 1, i - 1);
    }
  }

  //根据数组元素，先构造一个大顶堆
  public static void buildMaxHeap(int[] arr) {
    int len = arr.length;
    //从下标n/2处开始向前调整,调整到1的位置
    for (int i = len / 2; i > 0; i--) {
      //构建堆的时候，传入当前的调整下标，以及数组长度
      adjustHeap(arr, i, len);
    }
  }

  public static void adjustHeap(int[] arr, int index, int len) {
    //暂存父节点的值
    arr[0] = arr[index];
    //调整以index为根的子树
    for (int i = 2 * index; i < len; i = 2 * i) {
      //如果右孩子大一点
      // i<len-1 保证了 i+1<len
      if (i < len - 1 && arr[i] < arr[i + 1]) {
        i++;
      }

      //如果父节点的值大于等于孩子节点的值
      if (arr[0] >= arr[i]) {
        break;
      } else {
        //把孩子节点大的值放在index位置
        arr[index] = arr[i];
        //令index指向现在孩子的位置
        index = i;
      }
    }

    arr[index] = arr[0];

  }
}
