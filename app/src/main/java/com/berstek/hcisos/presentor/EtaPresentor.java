package com.berstek.hcisos.presentor;


import android.os.Handler;

import com.berstek.hcisos.firebase_da.DA;

/*
Calculates the ETA based on the absolute distance between two points, not the actual path they are
travelling
 */

public class EtaPresentor {

  private boolean firstRun = true;

  private double totalDistance = 0;
  private double initialDistance, currentDistance, difference;

  private long prevTimeStamp, currentTimeStamp;

  //make the numbers larger to avoid zero operations
  private double mult = 10000;

  private Handler handler = new Handler();

  public void calculateEta(double x, double y, double x1, double y1) {

    //first, calculate the initial distance
    if (firstRun) {
      initialDistance = Math.abs(calculateDistance(x, y, x1, y1));
      prevTimeStamp = System.currentTimeMillis();

      totalDistance = initialDistance;

    }

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        //then calculate the current distance between two points
        currentDistance = Math.abs(calculateDistance(x, y, x1, y1));
        //save when the last distance was recorded
        currentTimeStamp = System.currentTimeMillis();

        difference = Math.abs(initialDistance - currentDistance) ;
        firstRun = false;

        double unitPerSec = (difference / (Math.abs(currentTimeStamp - prevTimeStamp))) / 5;


        if (unitPerSec != 0) {
          try {
            double time = totalDistance / unitPerSec;
            double timeInMins = time / 60000;
            new DA().log((totalDistance / unitPerSec) / 1000);


            etaPresentorCallback.onEtaCalculated((int) timeInMins + "");

          } catch (ArithmeticException e) {

          }
        }

        calculateEta(x, y, x1, y1);
      }
    }, 2000);

  }

  private double calculateDistance(double x, double y, double x1, double y1) {
    return Math.sqrt((x1 * x1 - x * x) + (y1 * y1 - y * y));
  }

  private EtaPresentorCallback etaPresentorCallback;

  public interface EtaPresentorCallback {
    void onEtaCalculated(String minutes);
  }

  public void setEtaPresentorCallback(EtaPresentorCallback etaPresentorCallback) {
    this.etaPresentorCallback = etaPresentorCallback;
  }
}
