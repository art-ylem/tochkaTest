package com.example.tochkatest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private GoogleUseCase googleUseCase;
    private FacebookUseCase facebookUseCase;
    private VkUseCase vkUseCase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleUseCase = new GoogleUseCase(this);
        facebookUseCase = new FacebookUseCase(this);
        vkUseCase = new VkUseCase(this);

        //vk
        Button vkImg = findViewById(R.id.vk);
        vkImg.setOnClickListener(v -> vkUseCase.checkAuth());

        //google
        SignInButton googleImg = findViewById(R.id.gl);
        googleImg.setOnClickListener(v -> googleUseCase.signIn(this));

        //fb
        LoginButton fbBtn = findViewById(R.id.fb);
        fbBtn.setReadPermissions(Arrays.asList( "public_profile"));
        fbBtn.registerCallback(facebookUseCase.getCallbackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {facebookUseCase.useLoginInformation(loginResult);}
            @Override
            public void onCancel() {}
            @Override
            public void onError(FacebookException error) {}
        });

        //exit
        Button exit = findViewById(R.id.exitBtn);
        exit.setOnClickListener(v -> System.exit(0));
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookUseCase.onActivityResult(requestCode, resultCode, data);
        googleUseCase.onActivityResult(requestCode, resultCode, data);
        vkUseCase.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


    }


}
