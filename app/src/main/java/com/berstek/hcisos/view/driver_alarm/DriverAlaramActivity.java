package com.berstek.hcisos.view.driver_alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.berstek.hcisos.R;

public class DriverAlaramActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_driver_alaram);

    getSupportActionBar().hide();
  }
}
