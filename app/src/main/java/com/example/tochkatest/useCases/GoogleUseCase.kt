package com.example.tochkatest.useCases

import android.content.Intent
import android.util.Log
import com.example.tochkatest.view.MainActivity
import com.example.tochkatest.view.StartActivity
import com.example.tochkatest.model.User

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleUseCase(private val mainActivity: MainActivity) {


    private var user: User? = null
    private val RC_SIGN_IN = 0
    private val mGoogleSignInClient: GoogleSignInClient
    private var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    private var acct: GoogleSignInAccount? = null

    init {
        mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, gso)


    }

    //google
    fun signIn(mainActivity: MainActivity) {
        val signInIntent = mGoogleSignInClient.signInIntent
        mainActivity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, mainActivity: MainActivity) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            acct = GoogleSignIn.getLastSignedInAccount(mainActivity)
            user = User(acct!!.givenName + " " + acct!!.familyName, acct!!.photoUrl!!.toString())
            mainActivity.startActivity(Intent(mainActivity, StartActivity::class.java).putExtra("USER", user))


        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.statusCode)
        }

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            this.handleSignInResult(task, mainActivity)
        }
    }


}
