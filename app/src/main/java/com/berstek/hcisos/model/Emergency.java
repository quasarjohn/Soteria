package com.berstek.hcisos.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Emergency {

  private UserLocation user_location;
  private long time_stamp;
  private String type, details;
  private ArrayList<String> attachments_url;
  private Status status;

  private String rt_uid;

  @Exclude
  private String key;

  public UserLocation getUser_location() {
    return user_location;
  }

  public void setUser_location(UserLocation user_location) {
    this.user_location = user_location;
  }

  public long getTime_stamp() {
    return time_stamp;
  }

  public void setTime_stamp(long time_stamp) {
    this.time_stamp = time_stamp;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public ArrayList<String> getAttachments_url() {
    return attachments_url;
  }

  public void setAttachments_url(ArrayList<String> attachments_url) {
    this.attachments_url = attachments_url;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public enum Status {
    PENDING, DISPATCHED, RESLOVED
  }

  public String getRt_uid() {
    return rt_uid;
  }

  public void setRt_uid(String rt_uid) {
    this.rt_uid = rt_uid;
  }
}
