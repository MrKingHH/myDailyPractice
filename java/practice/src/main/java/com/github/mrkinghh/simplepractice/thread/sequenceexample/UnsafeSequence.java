package com.github.mrkinghh.simplepractice.thread.sequenceexample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther SamuelKing
 * @date 2020/3/28 11:29
 */

public class UnsafeSequence {

  private int value = 0;

  public void increaseValue() {
    System.out.println("increaseValue()方法的当前线程是"+Thread.currentThread().getName());
    value++;
  }
  public int getValue() {
    //还是主线程在调用这个方法
    System.out.println("getValue()方法的当前线程是"+Thread.currentThread().getName());
    return value;
  }

}
