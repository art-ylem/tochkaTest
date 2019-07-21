package com.example.tochkatest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private GoogleActivityPresenter googleActivityPresenter = new GoogleActivityPresenter(this);
    private FacebookActivityPresenter facebookActivityPresenter = new FacebookActivityPresenter(this);
    //google
    final int RC_SIGN_IN = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //vk
        Button vkImg = findViewById(R.id.vk);
        vkImg.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StartActivity.class)));

        //google
        SignInButton googleImg = findViewById(R.id.gl);
        googleImg.setOnClickListener(v -> googleActivityPresenter.signIn(this));




        //fb
        LoginButton fbBtn = findViewById(R.id.fb);

        fbBtn.setReadPermissions(Arrays.asList( "public_profile"));
        // Registering CallbackManager with the LoginButton
        fbBtn.registerCallback(facebookActivityPresenter.getCallbackManager(), new FacebookCallback<LoginResult>() {
//            AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                if (accessToken != null) {
//                useLoginInformation(accessToken);
//            }
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Retrieving access token using the LoginResult
                facebookActivityPresenter.useLoginInformation(loginResult);

            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });




        //exit
        Button exit = findViewById(R.id.exitBtn);
        exit.setOnClickListener(v -> System.exit(0));

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleActivityPresenter.handleSignInResult(task,this);
        } else{
            //fb
            facebookActivityPresenter.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }

    //google


    //fb




}
