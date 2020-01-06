package com.github.mrkinghh.simplepractice.thread;

import com.github.mrkinghh.simplepractice.thread.Class1;

/**
 * @author: SamuelKing
 * @date: 2019/10/26
 */
public class Class1Test {
//    @Test
//    public void test() {
//        Class1 class1 = new Class1("thread-1");
//        class1.start();
//        Class1 class2 = new Class1("thread-2");
//        class2.start();
//        System.out.println("Running main thread");
//    }

  public static void main(String[] args) {
    Class1 class1 = new Class1("thread-1");
    class1.start();
    Class1 class2 = new Class1("thread-2");
    class2.start();
    //加入下面两句 可以使得线程1 和 线程2在主线程之前执行。
    //否则就会先打印 Running main thread
    try {
      class1.join();
      class2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Running main thread");

  }
}
