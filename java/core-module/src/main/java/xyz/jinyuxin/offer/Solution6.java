package xyz.jinyuxin.offer;

import java.util.Stack;


/**
 * 面试题6：从头到尾打印链表(不能改变原有结构)
 */
public class Solution6 {
  public static void printListFromTailWithStack(ListNode list) {
    //从尾到头打印链表
    System.out.println("借助栈从尾到头打印链表: ");
    Stack<ListNode> stack = new Stack<>();
    while (list.next != null) {
      stack.push(list.next);
      list = list.next;
    }

    while (!stack.empty()) {
      System.out.print(stack.pop().data + " ");
    }
    System.out.println();
  }

}

