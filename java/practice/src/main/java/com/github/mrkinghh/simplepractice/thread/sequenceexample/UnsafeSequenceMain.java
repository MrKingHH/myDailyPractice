package com.github.mrkinghh.simplepractice.thread.sequenceexample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther SamuelKing
 * @date 2020/3/28 18:15
 */
//使用线程池的时候，目标类不需要实现Runnable或者继承Thread类。
public class UnsafeSequenceMain {
  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    UnsafeSequence us = new UnsafeSequence();

    //对内部变量做1000次加1操作,因为increase方法不是是同步的。所以结果会出错。
    for (int i = 0; i < 20; i++) {
      service.execute(us::increaseValue);
    }
    service.shutdown();

    Thread.currentThread().setName("UnsafeSequenceMain-Thread");
    System.out.println(Thread.currentThread().getName());
    System.out.println(us.getValue());
  }
}
