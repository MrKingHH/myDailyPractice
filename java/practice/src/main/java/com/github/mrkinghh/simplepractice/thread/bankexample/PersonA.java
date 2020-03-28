package com.github.mrkinghh.simplepractice.thread.bankexample;

/**
 * @auther SamuelKing
 * @date 2020/3/28 17:17
 */
public class PersonA implements Runnable {
  Bank bank;

  public PersonA(Bank bank) {
    this.bank = bank;
  }


  @Override
  public void run() {
    while (Bank.money.intValue() >= 200) {
      bank.getMoneyFromCounter(200);
    }

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
