package xyz.jinyuxin.dao;

import org.springframework.stereotype.Repository;
import xyz.jinyuxin.entity.User;

import java.util.List;

@Repository
public interface UserMapper {

  List<User> getAll();

  void addUser(User user);
}
