package com.github.mrkinghh.rpc.client;

import com.github.mrkinghh.rpc.framework.RpcFramework;
import com.github.mrkinghh.rpc.server.HelloWorldServiceImpl;

public class ClientMain {
  public static void main(String[] args) {
    RpcFramework rpc = new RpcFramework();
    HelloWorld hello = rpc.getReference(HelloWorld.class, "127.0.0.1", 1234);
    System.out.println(hello.sayHello("FYZ"));
  }
}
