package com.github.mrkinghh.offer;

import java.util.Stack;

/**
 * 面试题9：两个栈实现一个队列
 */
public class Solution9 {

  private static Stack<Number> stack1 = new Stack<>();
  private static Stack<Number> stack2 = new Stack<>();

  public static <T extends Number> void appandTail(T elem) {
    stack1.push(elem);
  }

  public static <T extends Number> Object deleteHead() {
    if (stack2.empty()) {
      while (!stack1.empty()) {
        stack2.push(stack1.pop());
      }
    }

    if (stack2.empty()) {
      throw new RuntimeException("queue is empty");
    }
    return stack2.pop();
  }
}
