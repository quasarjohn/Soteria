package com.berstek.hcisos.view.home;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.berstek.hcisos.R;
import com.berstek.hcisos.custom_classes.CustomDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FalseAlarmCheckFragment extends CustomDialogFragment
    implements View.OnClickListener {

  private Handler handler;

  private Button cancelBtn;
  private TextView timeTxt;

  private int startTime = 5;


  public FalseAlarmCheckFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_false_alarm_check, container, false);
    cancelBtn = view.findViewById(R.id.cancelBtn);
    timeTxt = view.findViewById(R.id.timeTxt);
    handler = new Handler();

    cancelBtn.setOnClickListener(this);

    countdown();

    super.onCreateView(inflater, container, savedInstanceState);
    return view;
  }

  private void countdown() {
    handler.postDelayed(countdownRunnable, 1000);
  }

  private Runnable countdownRunnable = new Runnable() {
    @Override
    public void run() {
      startTime--;

      if (getActivity() != null) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            timeTxt.setText(startTime + "s");
            if (startTime >= 1) {
              countdown();
            } else {
              falseAlarmCheckFragmentCallback.onCarAccidentDetected();
            }
          }
        });
      }
    }
  };

  private FalseAlarmCheckFragmentCallback falseAlarmCheckFragmentCallback;

  @Override
  public void onClick(View view) {
    super.dismiss();
  }

  public interface FalseAlarmCheckFragmentCallback {
    void onCarAccidentDetected();
  }

  public void setFalseAlarmCheckFragmentCallback(FalseAlarmCheckFragmentCallback falseAlarmCheckFragmentCallback) {
    this.falseAlarmCheckFragmentCallback = falseAlarmCheckFragmentCallback;
  }
}
