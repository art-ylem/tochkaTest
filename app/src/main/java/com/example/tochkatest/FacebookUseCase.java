package com.example.tochkatest;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookUseCase {

    private User user;

    public CallbackManager callbackManager;
    AccessToken accessToken;
    private MainActivity mainActivity;

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public FacebookUseCase(MainActivity mainActivity) {
        callbackManager = CallbackManager.Factory.create();
        this.mainActivity = mainActivity;


    }
    public void useLoginInformation(LoginResult loginResult) {
        accessToken = loginResult.getAccessToken();
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {

                    user = new User(object.getString("name"),
                            object.getJSONObject("picture").getJSONObject("data").getString("url"));

                    Intent intent = new Intent(mainActivity, StartActivity.class).putExtra("USER", user);
                    mainActivity.startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

}
