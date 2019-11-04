package com.github.mrkinghh.simplepractice.proxy;

import java.lang.reflect.Proxy;

import com.github.mrkinghh.simplepractice.proxy.client.RpcProxyClient;
import com.github.mrkinghh.simplepractice.proxy.server.HelloWorldInterface;
import com.github.mrkinghh.simplepractice.proxy.server.HelloWorldService;

public class ProxyTest {

  public static void main(String[] args) {
    // 创建中介类实例
    RpcProxyClient rpcProxyClient = new RpcProxyClient(new HelloWorldService());
    //获取代理类实例
    HelloWorldInterface helloWorldInterface = (HelloWorldInterface)
            Proxy.newProxyInstance(HelloWorldInterface.class.getClassLoader(),
                    new Class[]{HelloWorldInterface.class}, rpcProxyClient);
    helloWorldInterface.sayHello("hhhhh");

  }
}
