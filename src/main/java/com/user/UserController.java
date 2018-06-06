package com.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class UserController implements ErrorController {
  private static final String PATH = "/error";
  private final static int CACHE_MAX_SIZE = 10000;
  UserCache cache = new UserCache(CACHE_MAX_SIZE);

  Logger logger = LoggerFactory.getLogger(UserApplication.class);

  @Autowired
  UserService userService;

  @RequestMapping(value = PATH)
  public String error() {
    return "Error handling paramters: Not all parameters provided for this API, Please see the API requirements.";
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }

  @RequestMapping("/add")
  public String addUserInfo(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password) {

    if (cache.getCache().containsKey(username)){
      return "User already exist in DB.";
    }

//    int userId = userService.containUserInDb(username);
//    if (userId != -1) {
//      //cache miss, update cache
//      cache.getCache().put(username, userId);
//      return "User already exist in DB.";
//    }

    synchronized (this) {
      if (cache.getCache().containsKey(username)){
        return "User already exist in DB.";
      }

      User user = new User(username, password, userId);

      if (userService.addUserInDb(user) > 0) {
        cache.getCache().put(user.getUserName(), user.getUserID());
        logger.info("User saved successfully");
      }
    }
    return "User added succesfully";
  }

  @RequestMapping("/listAllUsers")
  public String listAllUsers() {
    StringBuilder sb = new StringBuilder();

    for(User user : userService.getAllUsersFromDb()){
      logger.info(user.toString());
      sb.append(user.toString());
    }

    return sb.toString();
  }

  @RequestMapping("/send")
  public String addUserInfo(@RequestParam(value = "user_from") String userFrom,
                            @RequestParam(value = "user_to") String userTo,
                            @RequestParam(value = "message") String message,
                            @RequestParam(value = "message_type") String message_type) {

    if (!cache.getCache().containsKey(userFrom) || !cache.getCache().containsKey(userTo)){
      if (userService.containUserInDb(userFrom) == -1 || userService.containUserInDb(userTo) == -1) {
        return "Users doesn't exist in DB.";
      }
    }

    int messageId;
    if (message_type == ENUM.TEXT)
    {
      messageId = userService.addMessageInDb(cache.getCache().get(userFrom), cache.getCache().get(userFrom), message);
      cache.getMessageCache(messageId, message);
    } else if (message_type == ENUM.IMAGE)
    {
      messageId = userService.addImageMessageInDb(cache.getCache().get(userFrom), cache.getCache().get(userFrom), message);
      cache.getMessageCache(messageId, message);
    } else
    {
      messageId = userService.addVideoMessageInDb(cache.getCache().get(userFrom), cache.getCache().get(userFrom), message);
      cache.getMessageCache(messageId, message);
    }

    Map<Integer, List<Integer>> messageList = cache.getUserCache(cache.getCache().get(userFrom));
    messageList.get(cache.getCache().get(userTo).append(messageId));
  }

  @RequestMapping("/fetch")
  public String addUserInfo(@RequestParam(value = "user_1") String userFrom,
                            @RequestParam(value = "user_2") String userTo) {

    if (!cache.getCache().containsKey(user1) || !cache.getCache().containsKey(user2)){
      if (userService.containUserInDb(user1) == -1 || userService.containUserInDb(user2) == -1) {
        return "Users doesn't exist in DB.";
      }
    }

    Set<Integer> messageIds = new HashSet();
    List<Integer> message1 = cache.getUserCache(cache.getCache().get(userFrom)).get(cache.getCache().get(userTo));
    List<Integer> message2 = cache.getUserCache(cache.getCache().get(userTo)).get(cache.getCache().get(userFrom));
    messageIds.addAll(message1).addAll(message2);

    //fetch messages from 

  }
