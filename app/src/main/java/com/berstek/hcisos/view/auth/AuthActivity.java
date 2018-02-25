package com.berstek.hcisos.view.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.berstek.hcisos.R;
import com.berstek.hcisos.callback.AuthCallback;

import com.berstek.hcisos.presentor.GoogleAuthPresentor;
import com.berstek.hcisos.utils.RequestCodes;
import com.berstek.hcisos.view.home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener,
    AuthCallback {

  private ImageView goggleImg, fbImg, twitterImg;

  /*
  Etong google auth presentor pre, dito nangyayari yung google authentication sa firebase.
  Imbes na nakasama yung code sa activity, naka encapsulate sya.
  Kapag successful yung login, mageemit lang sya ng onAuthSuccess()

   */
  private GoogleAuthPresentor googleAuth;
  private FirebaseAuth auth = FirebaseAuth.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);

    //make instance of google auth presentor
    googleAuth = new GoogleAuthPresentor(AuthActivity.this, auth);
    //set this activity as the target for its callbacks
    googleAuth.setGoogleAuthCallback(this);

    goggleImg = findViewById(R.id.googleImg);
    fbImg = findViewById(R.id.fbImg);
    twitterImg = findViewById(R.id.twitterImg);

    //google auth presentor implements onclick listener. when the button is clicked, it
    //starts the login procedure
    goggleImg.setOnClickListener(googleAuth);

    getSupportActionBar().hide();
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (auth.getCurrentUser() != null) {
      loadMainActivity();
    }
  }

  @Override
  public void onClick(View view) {
    int id = view.getId();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RequestCodes.SIGN_IN) {
      googleAuth.loginToFirebase(data);
    }
  }

  //overridden from AuthCallback
  @Override
  public void onAuthSuccess(FirebaseUser user) {
    loadMainActivity();
  }

  private void loadMainActivity() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
}
