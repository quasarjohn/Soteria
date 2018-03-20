package com.berstek.hcisos.presentor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.berstek.hcisos.firebase_da.DA;

public class AccelerometerPresentor implements SensorEventListener {

  private Activity activity;

  private SensorManager senSensorManager;
  private Sensor senAccelerometer;

  private long lastUpdate = 0;
  private float last_x, last_y, last_z;

  //1.7 meter per second or 6km per hour
  //increase this in production. But since I test the
  // change in acceleration manually, i have to set lower values
  private static final int SPEED_THRESHOLD = 17;

  public AccelerometerPresentor(Activity activity) {
    this.activity = activity;

    senSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

  }


  @Override
  public void onSensorChanged(SensorEvent sensorEvent) {
    Sensor mySensor = sensorEvent.sensor;

    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      float x = sensorEvent.values[0];
      float y = sensorEvent.values[1];
      float z = sensorEvent.values[2];

      long curTime = System.currentTimeMillis();

      if ((curTime - lastUpdate) > 100) {
        long diffTime = (curTime - lastUpdate);
        lastUpdate = curTime;

        float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 100;

        //if the sudden change in velocity is more than 60km/hour,
        // notify user that a distress call will be sent
        if (speed > SPEED_THRESHOLD) {
          //convert to km per hour
          int conversion = (int) (speed * 60 * 60 / 1000);
          new DA().log("VELOCITY CHANGED: " + conversion + " Km / hour");
          accelerometerPresentorCallback.onSuddenStop(conversion);
        }

        last_x = x;
        last_y = y;
        last_z = z;
      }
    }

  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {

  }

  private AccelerometerPresentorCallback accelerometerPresentorCallback;

  public interface AccelerometerPresentorCallback {
    void onSuddenStop(int speed);
  }

  public AccelerometerPresentorCallback getAccelerometerPresentorCallback() {
    return accelerometerPresentorCallback;
  }

  public void setAccelerometerPresentorCallback(AccelerometerPresentorCallback accelerometerPresentorCallback) {
    this.accelerometerPresentorCallback = accelerometerPresentorCallback;
  }
}
