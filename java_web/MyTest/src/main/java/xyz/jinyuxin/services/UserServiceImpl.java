package xyz.jinyuxin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.jinyuxin.dao.UserMapper;
import xyz.jinyuxin.entity.User;
import xyz.jinyuxin.services.IUserService;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  public UserMapper userDao;

  public void setUserMapper(UserMapper userMapper) {
    this.userDao = userMapper;
  }

  @Override
  public List<User> getUser() {
    List<User> userList = userDao.getAll();
    return userList;
  }

  @Override
  public void addUser(User user) {
    userDao.addUser(user);
  }


}
