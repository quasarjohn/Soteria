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
public class FalseAlarmCheckFragment extends CustomDialogFragment {

  private Handler handler;

  private Button cancelBtn;
  private TextView timeTxt;

  private int startTime = 20;

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

      getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          timeTxt.setText(startTime + " s");
          if(startTime >= 1) {
            countdown();
          }
          else {
            falseAlarmCheckFragmentCallback.onCarAccidentDetected();
          }
        }
      });
    }
  };

  private FalseAlarmCheckFragmentCallback falseAlarmCheckFragmentCallback;

  public interface FalseAlarmCheckFragmentCallback {
    void onCarAccidentDetected();
  }

  public void setFalseAlarmCheckFragmentCallback(FalseAlarmCheckFragmentCallback falseAlarmCheckFragmentCallback) {
    this.falseAlarmCheckFragmentCallback = falseAlarmCheckFragmentCallback;
  }
}
