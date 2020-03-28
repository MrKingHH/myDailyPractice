package com.github.mrkinghh.simplepractice.thread.bankexample;

/**
 * @auther SamuelKing
 * @date 2020/3/28 17:18
 */
public class PersonB implements Runnable {
  Bank bank;

  public PersonB(Bank bank) {
    this.bank = bank;
  }


  @Override
  public void run() {
    while (Bank.money.intValue() >= 100) {
      bank.getMoneyFromATM(100);
    }

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
