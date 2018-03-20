package com.berstek.hcisos.view.home;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.berstek.hcisos.R;
import com.berstek.hcisos.firebase_da.DA;
import com.berstek.hcisos.model.HelpSelection;
import com.berstek.hcisos.presentor.AccelerometerPresentor;
import com.berstek.hcisos.view.driver_alarm.DriverAlaramActivity;
import com.berstek.hcisos.view.help.HelpActivity;
import com.berstek.hcisos.view.help.HelpDetailsDialogFragment;
import com.berstek.hcisos.view.help.HelpSelectionDialogFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener, FalseAlarmCheckFragment.FalseAlarmCheckFragmentCallback,

    //listens to selection made on the HelpSelectionDialogFragment
    HelpSelectionDialogFragment.HelpSelectionDfCallback,

    //listens to action made on the HelpDetailsDialogFragment
    HelpDetailsDialogFragment.HelpDetailsDfCallback,

    AccelerometerPresentor.AccelerometerPresentorCallback {

  private Button mHelpBtn, mDriverAlarmBtn;

  private HelpSelectionDialogFragment helpSelectionDialogFragment;
  private HelpDetailsDialogFragment helpDetailsDialogFragment;

  private Bundle bundle;

  private AccelerometerPresentor accelerometerPresentor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    bundle = new Bundle();
    accelerometerPresentor = new AccelerometerPresentor(this);
    accelerometerPresentor.setAccelerometerPresentorCallback(this);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    mHelpBtn = findViewById(R.id.helpBtn);
    mDriverAlarmBtn = findViewById(R.id.driverAlarmBtn);

    mHelpBtn.setOnClickListener(this);
    mDriverAlarmBtn.setOnClickListener(this);

    helpSelectionDialogFragment = new HelpSelectionDialogFragment();
    helpSelectionDialogFragment.setHelpSelectionDfCallback(this);

    helpDetailsDialogFragment = new HelpDetailsDialogFragment();
    helpDetailsDialogFragment.setHelpDetailsDfCallback(this);
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }


  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onClick(View view) {
    int id = view.getId();

    if (id == R.id.helpBtn) {

      helpSelectionDialogFragment.show(getFragmentManager(), null);

    } else if (id == R.id.driverAlarmBtn) {
      Intent intent = new Intent(MainActivity.this, DriverAlaramActivity.class);
      startActivity(intent);
    }
  }

  //implemented from HelpSelectionDialogFragment.HelpSelectionDfCallback
  @Override
  public void onHelpSelectionSelected(HelpSelection helpSelection) {
    bundle.putString("selection", helpSelection.getTitle());
    helpDetailsDialogFragment.show(getFragmentManager(), null);
  }

  //implemented from HelpDetailsDialogFragment.HelpDetailsDfCallback
  @Override
  public void onHelpDetailsReady(String detail, ArrayList<String> img_urls) {
    bundle.putString("details", detail);
    Intent intent = new Intent(MainActivity.this, HelpActivity.class);
    intent.putExtras(bundle);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }

  private FalseAlarmCheckFragment checkFragment;

  @Override
  public void onSuddenStop(int speed) {
    if (checkFragment != null) {
      checkFragment.dismiss();
    }

    checkFragment = new FalseAlarmCheckFragment();
    checkFragment.setFalseAlarmCheckFragmentCallback(this);
    checkFragment.show(getFragmentManager(), null);
  }

  //if the user does not cancel, it will automatically send a distress call
  @Override
  public void onCarAccidentDetected() {
    bundle.putString("selection", "Car Accident");
    bundle.putString("details", "Automatically Triggered. " +
        "Possible car accident. Driver may have lost consciousness");
    Intent intent = new Intent(MainActivity.this, HelpActivity.class);
    intent.putExtras(bundle);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }

  @Override
  protected void onPause() {
    super.onPause();
    accelerometerPresentor.setAccelerometerPresentorCallback(null);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    accelerometerPresentor.setAccelerometerPresentorCallback(null);
  }
}
