package com.github.mrkinghh.simplepractice.thread.sequenceexample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther SamuelKing
 * @date 2020/3/28 18:25
 */

//使用线程池的时候，目标类不需要实现Runnable或者继承Thread类。
// 此处的SafeSequence就没有这两种操作。
public class SafeSequenceMain {
  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    SafeSequence ss = new SafeSequence();
    //对内部变量做1000次加1操作,因为increase方法是同步的。所以不会出错。
    for (int i = 0; i < 10; i++) {
      service.execute(ss::increaseValue);
    }
    service.shutdown();
    Thread.currentThread().setName("SafeSequenceMain-Thread");
    System.out.println(Thread.currentThread().getName());
    System.out.println(ss.getValue());
  }
}
