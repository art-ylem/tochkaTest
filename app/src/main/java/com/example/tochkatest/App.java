package com.example.tochkatest;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.vk.sdk.VKSdk;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
