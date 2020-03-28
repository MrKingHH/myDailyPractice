package com.github.mrkinghh.simplepractice.thread.atomicexample;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther SamuelKing
 * @date 2020/3/28 15:51
 */
public class AtomicStation implements Runnable {
  private static AtomicInteger tickets = new AtomicInteger(20);

  public AtomicStation() {
  }

  @Override
  public void run() {
    while (tickets.intValue() > 0) {
      synchronized ("this") {
        if (tickets.intValue() > 0) {
          int ticket = tickets.decrementAndGet();
          System.out.println(Thread.currentThread().getName() + "卖出了1张车票，还剩" + ticket);
        } else {
          System.out.println("票卖完了...");
        }
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
