package com.github.mrkinghh.simplepractice.thread.atomicexample;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther SamuelKing
 * @date 2020/3/28 17:06
 */
public class AtomicExample {

  //用AtomicInteger实现多线程计数相加
  public static AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 10000; i++) {
      new Thread(() -> {
        for (int j = 0; j < 10000; j++) {
          count.getAndIncrement();
        }
      }).start();
    }

    //让主线程休息一秒
    Thread.sleep(1000);
    System.out.println("AtomicInteger count: "+count);
  }

}
