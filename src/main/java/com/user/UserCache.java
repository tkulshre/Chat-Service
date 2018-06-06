package com.user;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;

public class UserCache {
  private static Map<String, Integer> cache;
  private static Map<Integer, <Integer, List<Integer>> userCache;
  //private static Map<Integer, String> messageCache;

  @SuppressWarnings("serial")
  public UserCache(final int CACHE_MAX_SIZE) {
    this.cache = Collections.synchronizedMap(new LinkedHashMap<String, Integer>(CACHE_MAX_SIZE, 0.75f, true) {
      protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return size() > CACHE_MAX_SIZE;
        }
    });
  }


  @SuppressWarnings("serial")
  public MessageCache(final int CACHE_MAX_SIZE) {
    this.userCache = Collections.synchronizedMap(new LinkedHashMap<Integer, <Integer, List<Integer>>(CACHE_MAX_SIZE, 0.75f, true) {
      protected boolean removeEldestEntry(Map.Entry<Integer, <Integer, List<Integer>> eldest) {
        return size() > CACHE_MAX_SIZE;
      }
    });
  }

  @SuppressWarnings("serial")
  public MessageCache(final int CACHE_MAX_SIZE) {
    this.messageCache = Collections.synchronizedMap(new LinkedHashMap<Integer, String>(CACHE_MAX_SIZE, 0.75f, true) {
      protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > CACHE_MAX_SIZE;
      }
    });
  }

  public Map<String, Integer> getCache(){
    return cache;
  }
  public Map<Integer, <Integer, List<Integer>> getUserCache() { return userCache; }
  public Map<Integer, String> getMessageCache() { return messageCache; }
}