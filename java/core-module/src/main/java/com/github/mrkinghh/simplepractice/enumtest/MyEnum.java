package com.github.mrkinghh.simplepractice.enumtest;

/**
 * @author: SamuelKing
 * @date: 2019/12/2
 */
public enum MyEnum {
  JIN_YU_XIN("金余新"), Samuel_King("金余新"), Wang_Qing("王清"), Tan_Sen("檀森");

  private String chineseName;

  MyEnum(String name){
    this.chineseName = name;
  }

  String getChineseName() {
    return this.chineseName;
  }

}
