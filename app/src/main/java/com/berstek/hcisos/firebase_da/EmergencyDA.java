package com.berstek.hcisos.firebase_da;

import com.berstek.hcisos.model.Emergency;
import com.google.android.gms.tasks.OnSuccessListener;

public class EmergencyDA extends DA {

  public void updateEmergency(Emergency emergency) {
    mRootRef.child("emergencies").child(auth.getCurrentUser().getUid()).setValue(emergency).
        addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {

      }
    });
  }

  public interface EmergencyDaCallback {
    void onEmergencyUpdated();
  }
}
