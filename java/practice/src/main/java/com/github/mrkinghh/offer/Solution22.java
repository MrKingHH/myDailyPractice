package com.github.mrkinghh.offer;

/**
 * 面试题22：链表倒数第K个节点
 */
public class Solution22 {

  public static ListNode findKElem(ListNode list, int k) {
    System.out.println("查找倒数第" + k + "个结点:");
    //如果没有元素或者倒数第0个，则返回头结点
    if (list.next == null || k == 0) {
      return list;
    }
    //指向第一个元素
    ListNode p1 = list.next;
    ListNode p2 = list.next;
    //p2先走k-1步
    for (int i = 0; i < k - 1; i++) {
      if (p2.next != null) {
        p2 = p2.next;
      } else {
        return list;
      }
    }

    while (p2.next != null) {
      p2 = p2.next;
      p1 = p1.next;
    }
    return p1;
  }
}
