package xyz.jinyuxin.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.jinyuxin.entity.User;

import java.util.List;

/**
 * @auther SamuelKing
 * @date 2020/2/28 17:04
 */
public class UserDaoTest {

  @Test
  public void test() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    UserMapper um = ac.getBean(UserMapper.class);
    List<User> userList = um.getAll();
    for (User user : userList) {
      System.out.println(user.getName() + "," + user.getAge());
    }
  }
}
