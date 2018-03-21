package com.berstek.hcisos.view.help;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.afollestad.assent.Assent;
import com.berstek.hcisos.R;
import com.berstek.hcisos.firebase_da.DA;
import com.berstek.hcisos.model.Emergency;
import com.berstek.hcisos.model.EtaDetails;
import com.berstek.hcisos.model.ResponseTeam;
import com.berstek.hcisos.model.UserLocation;
import com.berstek.hcisos.presentor.EtaPresentor;
import com.berstek.hcisos.presentor.HelpPresentor;
import com.berstek.hcisos.presentor.LocationPresentor;
import com.berstek.hcisos.utils.Utils;
import com.bumptech.glide.util.Util;


public class HelpActivity extends AppCompatActivity
    implements LocationPresentor.LocationPresentorCallback,
    EtaPresentor.EtaPresentorCallback, HelpPresentor.HelpPresentorCallback {

  //gets the location of the user
  private LocationPresentor locationPresentor;
  private String details, selection;

  //gets the eta of the response team
  private EtaPresentor etaPresentor;

  //gets the data of emergency and of the response team assigned to the user
  private HelpPresentor helpPresentor;

  private TextView etaTxt;

  private double lat, lang, lat1, lang1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help);
    getSupportActionBar().hide();

    etaTxt = findViewById(R.id.etaTxt);

    details = getIntent().getExtras().getString("details");
    selection = getIntent().getExtras().getString("selection");

    helpPresentor = new HelpPresentor();
    helpPresentor.setHelpPresentorCallback(this);
    helpPresentor.init(Utils.getUid());

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
    lat = userLocation.getLatitude();
    lang = userLocation.getLongitude();

    etaPresentor.getEta(lat, lang, lat1, lang1);
  }

  @Override
  public void onReverseGeocode(String address) {

  }


  @Override
  public void onEtaCalculated(EtaDetails eta) {
    etaTxt.setText(eta.getDuration().get("text").toString());
  }

  @Override
  public void onRtLoaded(ResponseTeam responseTeam) {
    UserLocation userLocation = responseTeam.getUser_location();
    lat1 = userLocation.getLatitude();
    lang1 = userLocation.getLongitude();

    etaPresentor.getEta(lat, lang, lat1, lang1);
  }

  @Override
  public void onEmergencyLoaded(Emergency emergency) {

  }
}
