package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

public class User {
  private String username;
  private String password;
  private int userId;

  public void User(String username, String password, int userId){
    this.username = username;
    this.password = password;
    this.userId = userId;
  }

  public String getUserName() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getUserId() {
    return userId;
  }

  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append(this.getUserName())
            .append(", ")
            .append(this.getPassword())
            .append(", ")
            .append(this.getUserId());

    return builder.toString();
  }

}
