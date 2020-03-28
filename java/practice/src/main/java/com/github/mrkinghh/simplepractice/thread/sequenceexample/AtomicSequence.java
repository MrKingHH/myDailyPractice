package com.github.mrkinghh.simplepractice.thread.sequenceexample;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther SamuelKing
 * @date 2020/3/28 18:49
 */
public class AtomicSequence {
  private AtomicInteger value = new AtomicInteger(0) ;

  public void increaseValue() {
    System.out.println("increaseValue()方法的当前线程是"+Thread.currentThread().getName());
    value.getAndIncrement();
  }

  public int getValue() {
    System.out.println("getValue()方法的当前线程是"+Thread.currentThread().getName());
    return value.intValue();
  }
}
