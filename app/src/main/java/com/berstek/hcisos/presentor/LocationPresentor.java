package com.berstek.hcisos.presentor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.os.Looper;

import com.berstek.hcisos.firebase_da.DA;
import com.berstek.hcisos.firebase_da.EmergencyDA;
import com.berstek.hcisos.model.Emergency;
import com.berstek.hcisos.model.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;


public class LocationPresentor {

  /*
  gets the user location and updates the data in firebase
   */

  private FusedLocationProviderClient fusedLocationProviderClient;
  private LocationRequest mLocationRequest;
  private LocationPresentorCallback locationPresentorCallback;

  private EmergencyDA emergencyDA;

  private String details, selection;

  private long timeStamp;


  public LocationPresentor(Activity activity) {
    timeStamp = System.currentTimeMillis();
    emergencyDA = new EmergencyDA();

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

    mLocationRequest = new LocationRequest();
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    mLocationRequest.setInterval(1000 * 5);
    mLocationRequest.setFastestInterval(1000);

    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
    builder.addLocationRequest(mLocationRequest);
    LocationSettingsRequest locationSettingsRequest = builder.build();

    // Check whether location settings are satisfied
    // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
    SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
    settingsClient.checkLocationSettings(locationSettingsRequest);
  }


  @SuppressLint("MissingPermission")
  public void getLocationUpdates() {

    fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
          @Override
          public void onLocationResult(LocationResult locationResult) {
            // do work here
            onLocationChanged(locationResult.getLastLocation());
          }
        },
        Looper.myLooper());
  }

  private void onLocationChanged(Location location) {
    UserLocation userLocation = new UserLocation();
    userLocation.setLatitude(location.getLatitude());
    userLocation.setLongitude(location.getLongitude());
    locationPresentorCallback.onLocationUpdated(userLocation);

    Emergency emergency = new Emergency();
    emergency.setUser_location(userLocation);
    emergency.setTime_stamp(timeStamp);
    emergency.setDetails(details);
    emergency.setType(selection);

    emergencyDA.updateEmergency(emergency);

    reverseGeocode(location.getLatitude(), location.getLongitude());
  }

  private void reverseGeocode(double latitude, double longitude) {
    locationPresentorCallback.onReverseGeocode(null);
  }


  public interface LocationPresentorCallback {
    void onLocationUpdated(UserLocation userLocation);

    void onReverseGeocode(String address);
  }

  public void setLocationPresentorCallback(LocationPresentorCallback locationPresentorCallback) {
    this.locationPresentorCallback = locationPresentorCallback;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public void setSelection(String selection) {
    this.selection = selection;
  }
}
