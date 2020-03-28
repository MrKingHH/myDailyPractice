package com.github.mrkinghh.simplepractice.thread.bankexample;


/**
 * @auther SamuelKing
 * @date 2020/3/28 17:26
 */
public class Main {

  public static void main(String[] args) {
    // 实力化一个银行对象
    Bank bank = new Bank();
    // 实例化两个人，传入同一个银行的对象
    PersonA personA = new PersonA(bank);
    PersonB personB = new PersonB(bank);

    Thread threadA = new Thread(personA, "personA");
    Thread threadB = new Thread(personB, "personB");

    threadA.start();
    threadB.start();
  }





}
