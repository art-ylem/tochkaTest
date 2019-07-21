package com.example.tochkatest.presenter

import android.util.Log

import com.example.tochkatest.network.VkService
import com.example.tochkatest.view.vkProfile.VkProfileView
import com.vk.sdk.VKAccessToken
import com.vk.sdk.api.VKApiConst

import java.util.HashMap

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class VkProfilePresenter(private val vkProfileView: VkProfileView) {

    fun loadData() {

        val map = HashMap<String, String>()
        map[VKApiConst.ACCESS_TOKEN] = VKAccessToken.currentToken().accessToken
        map[VKApiConst.VERSION] = 5.101.toString()
        map["fields"] = "photo_200"
        Log.e("TAG", "loadData: VK")

        VkService.instance
                .jsonApi
                .profileInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { ee -> vkProfileView.contactInfo(ee) }
                .subscribe()
    }

}
