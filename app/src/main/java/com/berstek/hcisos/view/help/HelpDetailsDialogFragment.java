package com.berstek.hcisos.view.help;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.berstek.hcisos.R;
import com.berstek.hcisos.custom_classes.CustomDialogFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpDetailsDialogFragment extends CustomDialogFragment
    implements View.OnClickListener {

  private EditText detailsEditTxt;
  private Button doneBtn;

  private ArrayList<String> img_urls;

  public HelpDetailsDialogFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_help_details_dialog, container, false);

    img_urls = new ArrayList<>();

    detailsEditTxt = view.findViewById(R.id.detailsEditTxt);
    doneBtn = view.findViewById(R.id.doneBtn);
    doneBtn.setOnClickListener(this);

    super.onCreateView(inflater, container, savedInstanceState);
    return view;
  }

  @Override
  public void onClick(View view) {
    helpDetailsDfCallback.onHelpDetailsReady(detailsEditTxt.getText().toString(), img_urls);
  }

  private HelpDetailsDfCallback helpDetailsDfCallback;

  public interface HelpDetailsDfCallback {
    void onHelpDetailsReady(String detail, ArrayList<String> img_urls);
  }

  public void setHelpDetailsDfCallback(HelpDetailsDfCallback helpDetailsDfCallback) {
    this.helpDetailsDfCallback = helpDetailsDfCallback;
  }
}
