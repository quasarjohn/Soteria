package com.berstek.hcisos.presentor;


import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

import com.berstek.hcisos.R;
import com.berstek.hcisos.firebase_da.DA;

/*
Calculates the ETA based on the absolute distance between two points, not the actual path they are
travelling
 */

public class EtaPresentor {

  private Handler handler = new Handler();

  private double lastDistance, updatedDistance, diffDistance;


  private long lastTime, currentTime;

  double diffTime;

  private Activity activity;

  double unitPerSec;

  private double mult = 1000000.0d;

  boolean firstRun = true;

  private TextView xTxt, yTxt, lastTxt, currentTxt, diffTxt, unitPerSecTxt;

  public EtaPresentor(Activity activity) {
    this.activity = activity;

    xTxt = activity.findViewById(R.id.xTxt);
    yTxt = activity.findViewById(R.id.yTxt);

    lastTxt = activity.findViewById(R.id.lastTxt);
    currentTxt = activity.findViewById(R.id.currentTxt);
    diffTxt = activity.findViewById(R.id.diffTxt);
    unitPerSecTxt = activity.findViewById(R.id.unitPerSecTxt);
  }

  public void calculateEta(double x, double y, double x1, double y1) {

    xTxt.setText(x + "");
    yTxt.setText(y + "");


    if (firstRun) {

      if (lastDistance == 0) {
        lastDistance = calculateDistance(x, y, x1, y1);
      }

      lastTime = System.currentTimeMillis();

      lastTxt.setText(lastDistance + " distance 5 secs ago");

      firstRun = false;
    }

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        updatedDistance = calculateDistance(x, y, x1, y1);
        currentTime = System.currentTimeMillis();

        diffDistance = Math.abs(lastDistance - updatedDistance);
        diffTime = currentTime - lastTime;

        new DA().log(diffDistance);

        try {
          unitPerSec = (diffDistance / (diffTime * 1.0)) * 5;

          double timeRemainingInSec = (updatedDistance / unitPerSec);

          if (unitPerSec > 0) {
            etaPresentorCallback.onEtaCalculated((int) timeRemainingInSec / 60 + "");
          }
          lastDistance = updatedDistance;

          activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              currentTxt.setText(updatedDistance + " current distance");
              diffTxt.setText(diffDistance + " in " + (diffTime / 5) + " secs");
              unitPerSecTxt.setText(unitPerSec + " units per secs");
            }
          });
        } catch (ArithmeticException e) {
          e.printStackTrace();
        }

        firstRun = true;


      }
    }, 5000);

  }

  private double calculateDistance(double x, double y, double x1, double y1) {
    double x2 = x1 - x;
    double y2 = y1 - y;
    return Math.sqrt((x2 * x2) + (y2 * y2)) * mult;
  }

  private EtaPresentorCallback etaPresentorCallback;

  public interface EtaPresentorCallback {
    void onEtaCalculated(String minutes);
  }

  public void setEtaPresentorCallback(EtaPresentorCallback etaPresentorCallback) {
    this.etaPresentorCallback = etaPresentorCallback;
  }
}
