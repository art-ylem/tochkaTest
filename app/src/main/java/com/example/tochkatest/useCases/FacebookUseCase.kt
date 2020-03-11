package com.example.tochkatest.useCases

import android.content.Intent
import android.os.Bundle
import com.example.tochkatest.view.MainActivity
import com.example.tochkatest.view.StartActivity
import com.example.tochkatest.model.User

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.GraphRequest
import com.facebook.login.LoginResult

import org.json.JSONException

class FacebookUseCase(private val mainActivity: MainActivity) {

    private var user: User? = null

    var callbackManager: CallbackManager
    internal var accessToken: AccessToken? = null

    init {
        callbackManager = CallbackManager.Factory.create()


    }

    fun useLoginInformation(loginResult: LoginResult) {
        accessToken = loginResult.accessToken
        /**
         * Creating the GraphRequest to fetch user details
         * 1st Param - AccessToken
         * 2nd Param - Callback (which will be invoked once the request is successful)
         */
        val request = GraphRequest.newMeRequest(accessToken) { `object`, _ ->
            //OnCompleted is invoked once the GraphRequest is successful
            try {

                user = User(`object`.getString("name"),
                        `object`.getJSONObject("picture").getJSONObject("data").getString("url"))

                val intent = Intent(mainActivity, StartActivity::class.java).putExtra("USER", user)
                mainActivity.startActivity(intent)


            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        // We set parameters to the GraphRequest using a Bundle.
        val parameters = Bundle()
        parameters.putString("fields", "id,name,picture.width(200)")
        request.parameters = parameters
        // Initiate the GraphRequest
        request.executeAsync()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}
