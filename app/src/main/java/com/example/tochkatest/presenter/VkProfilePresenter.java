package com.example.tochkatest.presenter;

import android.util.Log;

import com.example.tochkatest.network.VkService;
import com.example.tochkatest.view.vkProfile.VkProfileView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VkProfilePresenter {

    private VkProfileView vkProfileView;

    public VkProfilePresenter(VkProfileView vkProfileView) {
        this.vkProfileView = vkProfileView;
    }

    public void loadData(){

        HashMap<String, String> map = new HashMap<>();
        map.put(VKApiConst.ACCESS_TOKEN, VKAccessToken.currentToken().accessToken);
        map.put(VKApiConst.VERSION, String.valueOf(5.101));
        map.put("fields", "photo_200");
        Log.e("TAG", "loadData: VK" );

        VkService.getInstance()
                .getJSONApi()
                .profileInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext( ee -> vkProfileView.contactInfo(ee))
            .subscribe();
    }

}
