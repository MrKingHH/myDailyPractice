package com.github.mrkinghh.rpc.server;

import com.github.mrkinghh.rpc.client.HelloWorld;

public class HelloWorldServiceImpl implements HelloWorld {
  @Override
  public String sayHello(String content) {
    return "hello " + content;
  }
}
