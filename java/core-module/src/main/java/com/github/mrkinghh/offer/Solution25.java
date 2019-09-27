package com.github.mrkinghh.offer;

/**
 * 面试题25：合并两个有序的链表
 */
public class Solution25 {
  /**
   * 递归方式，不用新的链表。
   *
   * @param list1 不带头结点的链表1
   * @param list2 不带头结点的链表2
   * @return 不带头结点的新链表
   */

  public static ListNode mergeList(ListNode list1, ListNode list2) {
    //如果链表1为空，直接返回链表2
    if (list1 == null) {
      return list2;
    }

    //如果链表2为空，直接返回链表1
    if (list2 == null) {
      return list1;
    }

    ListNode newList;
    if (list1.data < list2.data) {
      newList = list1;
      newList.next = mergeList(list1.next, list2);
    } else {
      newList = list2;
      newList.next = mergeList(list1, list2.next);
    }

    return newList;
  }

  /**
   * 非递归方式，用一个新链表返回。
   *
   * @param list1 不带头结点的链表1
   * @param list2 不带头结点的链表2
   * @return 不带头结点的新链表
   */
  public static ListNode mergeList1(ListNode list1, ListNode list2) {
    //如果链表1为空，直接返回链表2
    if (list1 == null) {
      return list2;
    }

    //如果链表2为空，直接返回链表1
    if (list2 == null) {
      return list1;
    }

    //newList始终指向新链表的头结点
    ListNode newList = new ListNode();
    //curr作为新链表的遍历指针
    ListNode curr = newList;
    ListNode next;
    while (list1 != null && list2 != null) {
      if (list1.data <= list2.data) {
        next = new ListNode(list1.data);
        curr.next = next;
        curr = curr.next;
        list1 = list1.next;
      } else {
        next = new ListNode(list2.data);
        curr.next = next;
        curr = curr.next;
        list2 = list2.next;
      }
    }

    if (list1 != null) {
      curr.next = list1;
    }

    if (list2 != null) {
      curr.next = list2;
    }

    return newList.next;
  }

  /**
   * 非递归方式，用一个新链表返回。
   *
   * @param list1 不带头结点的链表1
   * @param list2 不带头结点的链表2
   * @return 不带头结点的新链表
   */
  public static ListNode mergeList2(ListNode list1, ListNode list2) {
    //如果链表1为空，直接返回链表2
    if (list1 == null) {
      return list2;
    }

    //如果链表2为空，直接返回链表1
    if (list2 == null) {
      return list1;
    }

    //head始终指向链表头结点
    ListNode head = null;
    //head指向首元素小的链表
    if (list1.data <= list2.data) {
      head = list1;
      list1 = list1.next;
    } else {
      head = list2;
      list2 = list2.next;
    }

    //作为比较指针
    ListNode curr = head;
    while (list1 != null && list2 != null) {
      if (list1.data <= list2.data) {
        curr.next = list1;
        curr = curr.next;
        list1 = list1.next;
      } else {
        curr.next = list2;
        curr = curr.next;
        list2 = list2.next;
      }
    }

    if (list1 != null) {
      curr.next = list1;
    }

    if (list2 != null) {
      curr.next = list2;
    }

    return head;
  }
}
