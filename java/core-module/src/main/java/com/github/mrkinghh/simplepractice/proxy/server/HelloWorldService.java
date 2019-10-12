package com.github.mrkinghh.simplepractice.proxy.server;

//委托类
public class HelloWorldService implements HelloWorldInterface {

  public HelloWorldService() {
  }

  @Override
  public void sayHello(String str) {
    System.out.println(str);
  }
}
