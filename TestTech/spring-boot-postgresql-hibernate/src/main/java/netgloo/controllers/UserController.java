package netgloo.controllers;

import netgloo.models.User;
import netgloo.models.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/user")
public class UserController {

  @Autowired
  private UserDao _userDao;
  
  @RequestMapping(value="/delete")
  @ResponseBody
  public String delete(String name) {
    try {
      User user = new User(name, "  ");
      _userDao.delete(user);
    }
    catch(Exception ex) {
      return ex.getMessage();
    }
    return "User succesfully deleted!";
  }
  
  @RequestMapping(value="/get-by-name")
  @ResponseBody
  public String getByName(String name) {
    User user;
    try {
      user = _userDao.getByName(name);
    }
    catch(Exception ex) {
      return "User not found";
    }
    return "The user id is: " + user.getName();
  }

  @RequestMapping(value="/save")
  @ResponseBody
  public String create(String name, String password) {
    try {
      User user = new User(name, password);
      _userDao.save(user);
    }
    catch(Exception ex) {
      return ex.getMessage();
    }
    return "User succesfully saved!";
  }

  @RequestMapping(value="/users")
  @ResponseBody
  public String getAllUsers() {

    List<User> users;

    try {
      users =_userDao.getAll();
    }
    catch(Exception ex) {
      return ex.getMessage();
    }

    String outputStr = "";
    for (User u : users) {
      outputStr += u.getName() + " ";
    }

    return outputStr;
  }

} // class UserController
