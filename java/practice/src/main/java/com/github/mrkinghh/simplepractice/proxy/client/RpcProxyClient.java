package com.github.mrkinghh.simplepractice.proxy.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcProxyClient implements InvocationHandler {

  //obj为委托类的对象
  private Object obj;

  public RpcProxyClient(Object obj) {
    this.obj = obj;
  }

  /**
   * 调用此方法执行
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("------插入前置通知代码-------------");
    Object result = method.invoke(obj, args);
    System.out.println("------插入后置处理代码-------------");
    return result;
  }

}
