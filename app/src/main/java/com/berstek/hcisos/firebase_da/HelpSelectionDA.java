package com.berstek.hcisos.firebase_da;

import com.berstek.hcisos.model.HelpSelection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HelpSelectionDA extends DA {

  /*
  Queries the list of selection for help and sends it to the presentor for processing
   */

  private HelpSelectionDaCallback daCallback;
  private ArrayList<HelpSelection> helpSelections;

  public void queryHelpSelections() {
    mRootRef.child("help_selections").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        helpSelections = new ArrayList<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
          HelpSelection selection = child.getValue(HelpSelection.class);
          helpSelections.add(selection);
        }

        /*
        this is to keep the number of items even and make the spacing in the flex box adapter even
         */
        if (helpSelections.size() % 2 != 0) {
          HelpSelection helpSelection = new HelpSelection();
          helpSelections.add(helpSelection);
          helpSelection.setTitle("");
          helpSelection.setImg_url("");
        }

        daCallback.onHelpSelectionsLoaded(helpSelections);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  public interface HelpSelectionDaCallback {
    void onHelpSelectionsLoaded(ArrayList<HelpSelection> helpSelections);
  }

  public void setDaCallback(HelpSelectionDaCallback daCallback) {
    this.daCallback = daCallback;
  }
}
