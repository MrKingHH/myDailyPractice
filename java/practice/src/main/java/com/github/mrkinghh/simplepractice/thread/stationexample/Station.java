package com.github.mrkinghh.simplepractice.thread.stationexample;

/**
 * @auther SamuelKing
 * @date 2020/3/28 15:20
 */
public class Station extends Thread {

  //站台的票数
  private static int tickets = 20;

  private static Object object = "lock";
  //在此处注入线程名
  public Station(String name) {
    super(name);
  }

  //重写run方法，实现卖票操作
  @Override
  public void run() {
    while (tickets > 0) {
      synchronized (object) {
        if (tickets > 0) {
          tickets--;
          System.out.println(getName() + "卖了1张车票，还剩" + tickets + "张车票。");
        } else {
          System.out.println("站台票卖完了");
        }
      }
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }


//    System.out.println(getName() + "的票卖完了。。。");

  }
}
