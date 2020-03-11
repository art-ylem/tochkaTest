package com.example.tochkatest.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import com.example.tochkatest.R
import com.example.tochkatest.useCases.FacebookUseCase
import com.example.tochkatest.useCases.GoogleUseCase
import com.example.tochkatest.useCases.VkUseCase

import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.SignInButton

import java.util.Arrays

class MainActivity : AppCompatActivity() {


    private var googleUseCase: GoogleUseCase? = null
    private var facebookUseCase: FacebookUseCase? = null
    private var vkUseCase: VkUseCase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        googleUseCase = GoogleUseCase(this)
        facebookUseCase = FacebookUseCase(this)
        vkUseCase = VkUseCase(this)

        //vk
        val vkImg = findViewById<Button>(R.id.vk)
        vkImg.setOnClickListener { vkUseCase!!.checkAuth() }

        //google
        val googleImg = findViewById<SignInButton>(R.id.gl)
        googleImg.setOnClickListener { googleUseCase!!.signIn(this) }

        //fb
        val fbBtn = findViewById<LoginButton>(R.id.fb)
        fbBtn.setReadPermissions(Arrays.asList("public_profile"))
        fbBtn.registerCallback(facebookUseCase!!.callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                facebookUseCase!!.useLoginInformation(loginResult)
            }

            override fun onCancel() {}
            override fun onError(error: FacebookException) {}
        })

    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            googleUseCase!!.onActivityResult(requestCode, resultCode, data)
            vkUseCase!!.onActivityResult(requestCode, resultCode, data)
            facebookUseCase!!.onActivityResult(requestCode, resultCode, data)
        }



    }


}
