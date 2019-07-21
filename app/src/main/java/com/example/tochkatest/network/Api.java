package com.example.tochkatest.network;


import com.example.tochkatest.model.git.GitUsers;
import com.example.tochkatest.model.vk.Profile;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Api {

    @GET("users.get")
    Observable<Profile> profileInfo(@QueryMap Map<String, String> map);

    @GET("users")
    Observable<ArrayList<GitUsers>> getGitUsersInfo(@QueryMap  Map<String, String> map);

}
