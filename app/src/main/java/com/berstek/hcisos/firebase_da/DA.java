package com.berstek.hcisos.firebase_da;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DA {

  public DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
  public FirebaseAuth auth = FirebaseAuth.getInstance();

  public void log(Object object) {
    mRootRef.child("logs").push().setValue(object);
  }


}
