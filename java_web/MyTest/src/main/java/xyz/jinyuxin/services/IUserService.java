package xyz.jinyuxin.services;

import xyz.jinyuxin.entity.User;

import java.util.List;

public interface IUserService {
  List<User> getUser();

  void addUser(User user);
}
