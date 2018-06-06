package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;

@Service
public class UserService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public synchronized int addUserInDb(User user){
    String sql = "INSERT INTO USER(userId, user_name, password) VALUES(?,?,?)";
    return jdbcTemplate.update(sql, user.getUserId(), user.getUserName(), user.getPassword());
  }

  public int containUserInDb(String username){
    try {
      String sql = "SELECT user_id FROM USER WHERE user_name = \'" + username + "\'";
      Integer userId = (Integer) jdbcTemplate.queryForObject(sql, Integer.class);
      return userId.intValue();
    } catch (EmptyResultDataAccessException e){
      return -1;
    }
  }

  public List<User> getAllUsersFromDb(){
    return jdbcTemplate.query("SELECT * FROM USER", new RowMapper<User>(){

      public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User user = new User(rs.getInt("user_id"), rs.getInt("user_name"), rs.getInt("password"));
        return user;
      }
    });
  }

  public synchronized int addMessageInDb(int userFrom, int userTo, String message){
    String sql = "INSERT INTO USER_MESSAGES(userFrom, userTo) VALUES(?,?)";
    int messageId = jdbcTemplate.update(sql, userFrom, userTo);
    String sql2 = "INSERT INTO MESSAGES(message_id, message, GETTIME()) VALUES(?,?)";
    jdbcTemplate.update(sql, messageId, message);
    return messageId;
  }

  public synchronized int addImageMessageInDb(int userFrom, int userTo, String imageLink){
    String sql = "INSERT INTO USER_MESSAGES(userFrom, userTo) VALUES(?,?)";
    int messageId = jdbcTemplate.update(sql, userFrom, userTo);
    String sql2 = "INSERT INTO MESSAGES(message_id, message, GETTIME()) VALUES(?,?)";
    jdbcTemplate.update(sql, messageId, imageLink);
    String sql2 = "INSERT INTO IMAGE_METADATA(messageId) VALUES(?)";
    jdbcTemplate.update(sql, messageId);
    return  messageId;
  }


  public synchronized int addVideoMessageInDb(int userFrom, int userTo, String videoLink){
    String sql = "INSERT INTO USER_MESSAGES(userFrom, userTo) VALUES(?,?)";
    int messageId = jdbcTemplate.update(sql, userFrom, userTo);
    String sql2 = "INSERT INTO MESSAGES(message_id, message, GETTIME()) VALUES(?,?)";
    jdbcTemplate.update(sql, messageId, videoLink);
    String sql2 = "INSERT INTO VIDEO_METADATA(messageId) VALUES(?)";
    jdbcTemplate.update(sql, messageId);
    return messageId;
  }
}
