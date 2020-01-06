package com.github.mrkinghh.simplepractice.proxy.client;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

  private Object target;

  public CglibProxy(Object target) {
    this.target = target;
  }

  public Object getProxyInstance() {
    //工具类
    Enhancer enhancer = new Enhancer();
    //设置父类
    enhancer.setSuperclass(target.getClass());
    //设置回调函数
    enhancer.setCallback(this);
    //创建子类对象代理
    return enhancer.create();
  }

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
    System.out.println("开启事务...");
    Object o1 = method.invoke(target, objects);
    System.out.println("结束事务...");
    return o1;
  }
}
