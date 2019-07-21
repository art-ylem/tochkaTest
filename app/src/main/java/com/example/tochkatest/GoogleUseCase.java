package com.example.tochkatest;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleUseCase {


    private User user;
    final int RC_SIGN_IN = 0;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    private MainActivity mainActivity;
    private GoogleSignInAccount acct;

    public GoogleUseCase(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, gso);


    }

    //google
    public void signIn(MainActivity mainActivity) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mainActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask, MainActivity mainActivity ) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            acct = GoogleSignIn.getLastSignedInAccount(mainActivity);
            user = new User(acct.getGivenName() + " " + acct.getFamilyName(), acct.getPhotoUrl().toString());
            mainActivity.startActivity(new Intent(mainActivity, StartActivity.class).putExtra("USER", user));


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task,mainActivity);
        }
    }




}
