package com.berstek.hcisos.view.help;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.afollestad.assent.Assent;
import com.berstek.hcisos.R;
import com.berstek.hcisos.firebase_da.DA;
import com.berstek.hcisos.model.UserLocation;
import com.berstek.hcisos.presentor.EtaPresentor;
import com.berstek.hcisos.presentor.LocationPresentor;


public class HelpActivity extends AppCompatActivity
    implements LocationPresentor.LocationPresentorCallback, EtaPresentor.EtaPresentorCallback {

  private LocationPresentor locationPresentor;
  private String details, selection;

  private EtaPresentor etaPresentor;

  private TextView etaTxt;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help);
    getSupportActionBar().hide();

    etaTxt = findViewById(R.id.etaTxt);

    details = getIntent().getExtras().getString("details");
    selection = getIntent().getExtras().getString("selection");

    etaPresentor = new EtaPresentor(this);
    etaPresentor.setEtaPresentorCallback(this);

    Assent.setActivity(this, this);

    locationPresentor = new LocationPresentor(this);
    locationPresentor.setDetails(details);
    locationPresentor.setSelection(selection);
    locationPresentor.setLocationPresentorCallback(this);

    if (!Assent.isPermissionGranted(Assent.ACCESS_FINE_LOCATION)) {
      Assent.requestPermissions(result -> {
      }, 69, Assent.ACCESS_FINE_LOCATION);
    } else {
      locationPresentor.getLocationUpdates();
    }
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    Assent.handleResult(permissions, grantResults);

    locationPresentor.getLocationUpdates();
  }

  @Override
  public void onLocationUpdated(UserLocation userLocation) {
    //do something with the userLocation object
    //temporarily using neu coordinates
    etaPresentor.calculateEta(userLocation.getLatitude(),
        userLocation.getLongitute(), 14.7192, 121.1071);
  }

  @Override
  public void onReverseGeocode(String address) {

  }

  @Override
  public void onEtaCalculated(String minutes) {
    etaTxt.setText(minutes);
  }
}
