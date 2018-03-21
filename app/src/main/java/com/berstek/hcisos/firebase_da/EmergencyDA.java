package com.berstek.hcisos.firebase_da;

import com.berstek.hcisos.model.Emergency;
import com.berstek.hcisos.model.UserLocation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.Query;

public class EmergencyDA extends DA {

  public void updateEmergency(Emergency emergency) {
    mRootRef.child("emergencies").child(auth.getCurrentUser().getUid()).setValue(emergency).
        addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {

          }
        });
  }

  public void updateEmergencyLocation(UserLocation userLocation) {
    mRootRef.child("emergencies").child(auth.getCurrentUser().
        getUid()).child("user_location").setValue(userLocation);
  }

  public Query queryEmergencyByUid(String uid) {
    return mRootRef.child("emergencies").orderByKey().equalTo(uid);
  }

  public interface EmergencyDaCallback {
    void onEmergencyUpdated();
  }
}
