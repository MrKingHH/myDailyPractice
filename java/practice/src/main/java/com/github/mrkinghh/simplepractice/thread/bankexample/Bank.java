package com.github.mrkinghh.simplepractice.thread.bankexample;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther SamuelKing
 * @date 2020/3/28 17:17
 */
public class Bank {
  //银行有100块钱
  public static AtomicInteger money = new AtomicInteger(1000);

  public void getMoneyFromCounter(int money) {
    Bank.money.set(Bank.money.intValue()-money);
    System.out.println(Thread.currentThread().getName()+"从柜台取走了"+money+"钱，还剩下"+Bank.money.intValue());
  }

  public void getMoneyFromATM(int money) {
    Bank.money.set(Bank.money.intValue()-money);
    System.out.println(Thread.currentThread().getName()+"从ATM取走了"+money+"钱，还剩下"+Bank.money.intValue());
  }

}
