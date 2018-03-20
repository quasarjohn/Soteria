package com.berstek.hcisos.model;

import java.util.HashMap;

public class ResponseTeam {

  private String leader_uid;
  private HashMap<String, Integer> members;

  //uid of the emergency the team is dispatched to
  private String dispatched_to;
  private long dispatched_on;

  public String getLeader_uid() {
    return leader_uid;
  }

  public void setLeader_uid(String leader_uid) {
    this.leader_uid = leader_uid;
  }

  public HashMap<String, Integer> getMembers() {
    return members;
  }

  public void setMembers(HashMap<String, Integer> members) {
    this.members = members;
  }

  public String getDispatched_to() {
    return dispatched_to;
  }

  public void setDispatched_to(String dispatched_to) {
    this.dispatched_to = dispatched_to;
  }

  public long getDispatched_on() {
    return dispatched_on;
  }

  public void setDispatched_on(long dispatched_on) {
    this.dispatched_on = dispatched_on;
  }
}
