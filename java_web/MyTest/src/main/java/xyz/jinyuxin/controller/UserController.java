package xyz.jinyuxin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.jinyuxin.entity.User;
import xyz.jinyuxin.services.IUserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

  @Qualifier("userServiceImpl")
  @Autowired
  private IUserService userService;

  @RequestMapping(value = "/query", method = RequestMethod.GET)
  public String getAllUser(Model model) {
    List<User> userList = userService.getUser();
    model.addAttribute("queryAll",userList);
    return "queryAll";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addUser(User user) {
    userService.addUser(user);
    return "addUser";
  }

}
