package com.berstek.hcisos.presentor;

import com.berstek.hcisos.firebase_da.DA;
import com.berstek.hcisos.firebase_da.EmergencyDA;
import com.berstek.hcisos.firebase_da.RtDA;
import com.berstek.hcisos.model.Emergency;
import com.berstek.hcisos.model.ResponseTeam;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.opencv.ml.EM;

public class HelpPresentor {


  private RtDA rtDA;
  private EmergencyDA emergencyDA;
  /*
  gets the data and location of rt assigned to the user
   */
  private HelpPresentorCallback helpPresentorCallback;

  public void init(String emergencyUid) {
    rtDA = new RtDA();
    emergencyDA = new EmergencyDA();

    emergencyDA.queryEmergencyByUid(emergencyUid).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        new DA().log(dataSnapshot.getKey());

        Emergency emergency = dataSnapshot.getValue(Emergency.class);
        emergency.setKey(dataSnapshot.getKey());
        helpPresentorCallback.onEmergencyLoaded(emergency);

        if (emergency.getRt_uid() != null)
          queryRt(emergency.getRt_uid());
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Emergency emergency = dataSnapshot.getValue(Emergency.class);
        emergency.setKey(dataSnapshot.getKey());
        helpPresentorCallback.onEmergencyLoaded(emergency);

        if (emergency.getRt_uid() != null)
          queryRt(emergency.getRt_uid());
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  public interface HelpPresentorCallback {
    void onRtLoaded(ResponseTeam responseTeam);

    void onEmergencyLoaded(Emergency emergency);
  }

  public void setHelpPresentorCallback(HelpPresentorCallback helpPresentorCallback) {
    this.helpPresentorCallback = helpPresentorCallback;
  }

  private void queryRt(String uid) {
    rtDA.queryRtByUid(uid).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        ResponseTeam responseTeam = dataSnapshot.getValue(ResponseTeam.class);
        helpPresentorCallback.onRtLoaded(responseTeam);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }
}
