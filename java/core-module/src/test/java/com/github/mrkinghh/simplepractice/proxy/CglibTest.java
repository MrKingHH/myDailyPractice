package com.github.mrkinghh.simplepractice.proxy;

import com.github.mrkinghh.simplepractice.proxy.client.CglibProxy;
import com.github.mrkinghh.simplepractice.proxy.server.CglibService;

public class CglibTest {
  public static void main(String[] args) {
    //委托对象
    CglibService service = new CglibService();
    //代理对象
    CglibService proxy = (CglibService) new CglibProxy(service).getProxyInstance();
    proxy.sayHi();
  }
}
