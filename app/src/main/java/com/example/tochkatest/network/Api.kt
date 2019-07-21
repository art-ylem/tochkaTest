package com.example.tochkatest.network


import com.example.tochkatest.model.git.GitUsers
import com.example.tochkatest.model.git.InputData
import com.example.tochkatest.model.vk.Profile

import java.util.ArrayList

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {

    @GET("users.get")
    fun profileInfo(@QueryMap map: Map<String, String>): Observable<Profile>

    @GET("users")
    fun getGitUsersInfo(@QueryMap map: Map<String, String>): Observable<ArrayList<GitUsers>>

    @GET("search/users")
    fun getGitInputUsersInfo(@QueryMap map: Map<String, String>): Observable<InputData>

}
