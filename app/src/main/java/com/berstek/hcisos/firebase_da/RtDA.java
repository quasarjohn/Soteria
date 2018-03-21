package com.berstek.hcisos.firebase_da;

import com.google.firebase.database.Query;

public class RtDA extends DA {

  public Query queryRtByUid(String uid) {
    return mRootRef.child("response_teams").child(uid);
  }
}
