package xyz.jinyuxin.services;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.jinyuxin.entity.User;
import xyz.jinyuxin.services.IUserService;

import java.util.List;


/**
 * @auther SamuelKing
 * @date 2020/2/28 2:16
 */
public class UserServiceTest {

  @Test
  public void test() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    IUserService bean = ac.getBean(IUserService.class);
    List<User> userList = bean.getUser();
    for (User user : userList) {
      System.out.println(user.getName() + "," + user.getAge());
    }
  }


}
