package xyz.jinyuxin.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 * @auther SamuelKing
 * @date 2020/2/28 17:09
 */
public class UserControllerTest {

  @Test
  public void test() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    UserController uc = ac.getBean(UserController.class);
    Model model = new ExtendedModelMap();
    String result = uc.getAllUser(model);
    Assert.assertEquals("queryAll", result);
  }

}
