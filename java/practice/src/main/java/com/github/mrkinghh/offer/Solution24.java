package com.github.mrkinghh.offer;

/**
 * 面试题24：反转链表
 */
public class Solution24 {

  public static ListNode reverseList(ListNode list) {
    //如果没有元素，就返回头结点
    if (list.next == null) {
      return list;
    }
    //指向链表头结点
    ListNode head = list;
    //指向第一个元素节点
    ListNode p = head.next;
    //暂存后面的节点
    ListNode next = head.next;
    //暂存前面的节点
    ListNode pre = null;
    //如果后面一个节点存在
    while (p.next != null) {
      next = p.next;
      p.next = pre;
      pre = p;
      p = next;
    }
    //将最后一个结点跟前面的节点连接上
    p.next = pre;
    //最后令头结点指向首元素节点
    head.next = p;
    return head;
  }
}
