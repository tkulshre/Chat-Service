package com.user;

public enum Message {
  TEXT(0),
  IMAGE(1),
  VIDEO(2)

  private int numVal;

  Message(int numVal) {
    this.numVal = numVal;
  }

  public int getNumVal() {
    return this.numVal;
  }
}
