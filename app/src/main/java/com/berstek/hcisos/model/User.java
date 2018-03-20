package com.berstek.hcisos.model;

public class User {

  private String email, last_name, first_name, photo_url;
  private long date_joined;

  //this only applies if the user is a member of the RT
  private String leader_uid;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getPhoto_url() {
    return photo_url;
  }

  public void setPhoto_url(String photo_url) {
    this.photo_url = photo_url;
  }

  public long getDate_joined() {
    return date_joined;
  }

  public void setDate_joined(long date_joined) {
    this.date_joined = date_joined;
  }

  public String getLeader_uid() {
    return leader_uid;
  }

  public void setLeader_uid(String leader_uid) {
    this.leader_uid = leader_uid;
  }
}
