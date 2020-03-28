package com.github.mrkinghh.simplepractice.thread.atomicexample;

/**
 * @auther SamuelKing
 * @date 2020/3/28 16:55
 */
public class Main {
  public static void main(String[] args) throws InterruptedException {

    AtomicStation as1 = new AtomicStation();
    AtomicStation as2 = new AtomicStation();
    AtomicStation as3 = new AtomicStation();

    Thread thread1 = new Thread(as1, "window1");
    Thread thread2 = new Thread(as2, "window2");
    Thread thread3 = new Thread(as3, "window3");

    thread1.start();
    thread2.start();
    thread3.start();

    Thread.sleep(10000);
    System.out.println(Thread.currentThread().getName());

  }
}
