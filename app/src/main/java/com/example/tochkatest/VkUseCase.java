package com.example.tochkatest;

import android.content.Intent;

import androidx.appcompat.app.ActionBarDrawerToggle;

import com.example.tochkatest.model.vk.Profile;
import com.example.tochkatest.presenter.VkProfilePresenter;
import com.example.tochkatest.view.gitUsers.FragmentGitUsers;
import com.example.tochkatest.view.vkProfile.VkProfileView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class VkUseCase implements VkProfileView {

    public static final String[] DEFAULT_LOGIN_SCOPE = {VKScope.WALL, VKScope.PHOTOS};
    private VkProfilePresenter vkProfilePresenter;
    private MainActivity mainActivity;

    public User getUser() {
        return user;
    }

    private User user;

    public VkUseCase(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void contactInfo(Profile obj) {

        user = new User(obj.getResponse().get(0).getFirstName() + " " + obj.getResponse().get(0).getLastName(),
                obj.getResponse().get(0).getPhoto200());

        mainActivity.startActivity(new Intent(mainActivity, StartActivity.class).putExtra("USER", user));


    }

    public void checkAuth() {
        if (!CurrentUser.isAuthorized()) {
            VKSdk.login(mainActivity, DEFAULT_LOGIN_SCOPE);
        } else {
            vkProfilePresenter = new VkProfilePresenter(this);
            vkProfilePresenter.loadData();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

            if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
                @Override
                public void onResult(VKAccessToken res) {
                    checkAuth();
                }
                @Override
                public void onError(VKError error) {}
            })) {
                mainActivity.onActivityResult(requestCode, resultCode, data);
            }

    }


}
