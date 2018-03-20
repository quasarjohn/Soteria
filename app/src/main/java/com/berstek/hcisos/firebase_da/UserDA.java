package com.berstek.hcisos.firebase_da;

import com.berstek.hcisos.model.User;
import com.berstek.hcisos.utils.Utils;

public class UserDA extends DA {

  public void addUser(User user) {
    mRootRef.child("users").child(Utils.getUid()).setValue(user);
  }
}
