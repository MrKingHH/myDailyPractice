package com.github.mrkinghh.simplepractice.enumtest;

/**
 * @author: SamuelKing
 * @date: 2019/12/2
 */
public class MyEnumTest {
  public static void main(String[] args) {
    for(MyEnum chineseName : MyEnum.values()) {
      System.out.println("chineseName = "+ chineseName);
      System.out.println("chineseName.name = "+ chineseName.name());
      System.out.println("chineseName.getChineseName = "+ chineseName.getChineseName());
      System.out.println("ordinal = "+ chineseName.ordinal());
    }
  }
}
