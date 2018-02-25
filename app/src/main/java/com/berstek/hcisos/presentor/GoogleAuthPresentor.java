package com.berstek.hcisos.presentor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.berstek.hcisos.callback.AuthCallback;
import com.berstek.hcisos.utils.RequestCodes;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class GoogleAuthPresentor implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

  private AppCompatActivity activity;

  private GoogleSignInClient googleSignInClient;
  private FirebaseAuth auth;

  private AuthCallback googleAuthCallback;

  public GoogleAuthPresentor(AppCompatActivity activity, FirebaseAuth auth) {
    this.activity = activity;
    this.auth = auth;


    //use your own request ID token from google credential manager
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("114863922096-h75ndql3h2cuhp6hm7atrcfh1ef64r68.apps.googleusercontent.com")
        .requestEmail()
        .build();

    googleSignInClient = GoogleSignIn.getClient(activity, gso);
  }


  @Override
  public void onClick(View view) {
    Intent signInIntent = googleSignInClient.getSignInIntent();
    activity.startActivityForResult(signInIntent, RequestCodes.SIGN_IN);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  public void loginToFirebase(Intent data) {
    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

    if (result.isSuccess()) {
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        GoogleSignInAccount account = task.getResult(ApiException.class);

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  FirebaseUser user = auth.getCurrentUser();
                  googleAuthCallback.onAuthSuccess(user);

                } else {
                  task.getException().printStackTrace();
                }
              }
            });
      } catch (ApiException e) {
        e.printStackTrace();
      }
    }
  }


  public void setGoogleAuthCallback(AuthCallback googleAuthCallback) {
    this.googleAuthCallback = googleAuthCallback;
  }
}
