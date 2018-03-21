package com.berstek.hcisos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

@JsonIgnoreProperties({"traffic_speed_entry", "via_waypoint"})
public class EtaDetails {

  //text(string), value(double)
  private HashMap<String, Object> distance;

  //text(string), value(double)
  private HashMap<String, Object> duration;

  //lat(double), lng(double)
  private HashMap<String, Object> end_location;

  //lat(double), lng(double)
  private HashMap<String, Object> start_location;

  private String start_address, end_address;

  private Object steps;


  public HashMap<String, Object> getDistance() {
    return distance;
  }

  public void setDistance(HashMap<String, Object> distance) {
    this.distance = distance;
  }

  public HashMap<String, Object> getDuration() {
    return duration;
  }

  public void setDuration(HashMap<String, Object> duration) {
    this.duration = duration;
  }


  public Object getSteps() {
    return steps;
  }

  public void setSteps(Object steps) {
    this.steps = steps;
  }

  public HashMap<String, Object> getEnd_location() {
    return end_location;
  }

  public void setEnd_location(HashMap<String, Object> end_location) {
    this.end_location = end_location;
  }

  public HashMap<String, Object> getStart_location() {
    return start_location;
  }

  public void setStart_location(HashMap<String, Object> start_location) {
    this.start_location = start_location;
  }

  public String getStart_address() {
    return start_address;
  }

  public void setStart_address(String start_address) {
    this.start_address = start_address;
  }

  public String getEnd_address() {
    return end_address;
  }

  public void setEnd_address(String end_address) {
    this.end_address = end_address;
  }
}
