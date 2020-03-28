package com.github.mrkinghh.simplepractice.thread.sequenceexample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther SamuelKing
 * @date 2020/3/28 18:49
 */
public class AtomicSequenceMain {
  public static void main(String[] args) throws InterruptedException {
    ExecutorService service = Executors.newCachedThreadPool();
    AtomicSequence as = new AtomicSequence();
    for (int i =0; i<100;i++){
      service.execute(as::increaseValue);
    }

    service.shutdown();
    //任务完成 或者 超出指定时间就不会阻塞后面代码。否则就阻塞。
    service.awaitTermination(1, TimeUnit.SECONDS);
    Thread.currentThread().setName("AtomicSequenceMain-Thread");
    System.out.println(Thread.currentThread().getName());
    System.out.println("value的值是："+as.getValue());
  }
}
