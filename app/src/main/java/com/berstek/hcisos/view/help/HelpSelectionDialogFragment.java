package com.berstek.hcisos.view.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.hcisos.R;
import com.berstek.hcisos.callback.RecviewItemClickCallback;
import com.berstek.hcisos.custom_classes.CustomDialogFragment;
import com.berstek.hcisos.firebase_da.DA;
import com.berstek.hcisos.model.HelpSelection;
import com.berstek.hcisos.presentor.HelpSelectionPresentor;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class HelpSelectionDialogFragment extends CustomDialogFragment
    implements HelpSelectionPresentor.HelpSelectionPresentorCallback, RecviewItemClickCallback {

  private FlexboxLayoutManager mLayoutManager;
  private RecyclerView mHelpSelectionRecview;
  private HelpSelectionAdapter mHelpSelectionAdapter;
  private HelpSelectionPresentor mHelpSelectionPresentor;
  private ArrayList<HelpSelection> helpSelections;

  public HelpSelectionDialogFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_help_selection_dialog, container, false);
    super.onCreateView(inflater, container, savedInstanceState);

    init();

    return view;
  }

  private void init() {
    mHelpSelectionRecview = view.findViewById(R.id.helpSelectionRecview);

    mLayoutManager = new FlexboxLayoutManager(getActivity());
    mLayoutManager.setFlexDirection(FlexDirection.ROW);
    mLayoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);

    mHelpSelectionRecview.setLayoutManager(mLayoutManager);

    mHelpSelectionPresentor = new HelpSelectionPresentor();
    mHelpSelectionPresentor.setPresentorCallback(this);
  }


  @Override
  public void onHelpSelectionsLoaded(ArrayList<HelpSelection> h) {
    this.helpSelections = h;
    mHelpSelectionAdapter = new HelpSelectionAdapter(getActivity(), helpSelections);
    mHelpSelectionAdapter.setRecviewItemClickCallback(this);
    mHelpSelectionRecview.setAdapter(mHelpSelectionAdapter);
  }

  @Override
  public void onRecviewItemClick(View view, int position) {
    HelpSelection helpSelection = helpSelections.get(position);
    helpSelectionDfCallback.onHelpSelectionSelected(helpSelection);
  }

  private HelpSelectionDfCallback helpSelectionDfCallback;

  public interface HelpSelectionDfCallback {
    void onHelpSelectionSelected(HelpSelection helpSelection);
  }

  public void setHelpSelectionDfCallback(HelpSelectionDfCallback helpSelectionDfCallback) {
    this.helpSelectionDfCallback = helpSelectionDfCallback;
  }
}
