package com.berstek.hcisos.presentor;

import com.berstek.hcisos.firebase_da.HelpSelectionDA;
import com.berstek.hcisos.model.HelpSelection;

import java.util.ArrayList;

public class HelpSelectionPresentor implements HelpSelectionDA.HelpSelectionDaCallback {

  /*
  Does any processing for input and output data for help selection adapter and also controls
  the state of its views
   */

  private HelpSelectionDA helpSelectionDA;
  private HelpSelectionPresentorCallback presentorCallback;

  public HelpSelectionPresentor() {
    this.helpSelectionDA = new HelpSelectionDA();
    helpSelectionDA.setDaCallback(this);
    helpSelectionDA.queryHelpSelections();
  }

  @Override
  public void onHelpSelectionsLoaded(ArrayList<HelpSelection> helpSelections) {
    presentorCallback.onHelpSelectionsLoaded(helpSelections);
  }

  public interface HelpSelectionPresentorCallback {
    void onHelpSelectionsLoaded(ArrayList<HelpSelection> helpSelections);
  }

  public void setPresentorCallback(HelpSelectionPresentorCallback presentorCallback) {
    this.presentorCallback = presentorCallback;
  }
}
