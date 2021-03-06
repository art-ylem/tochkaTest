package com.example.tochkatest.network


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitService private constructor() {
    private val mRetrofit: Retrofit


    //привязываем Api.class (интерфейс) к Retrofit
    val jsonApi: Api
        get() = mRetrofit.create(Api::class.java)

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)

        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//из json в объект
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rx
                .client(client.build())
                .build()
    }

    companion object {

        private val BASE_URL = "https://api.github.com/"
        val instance: GitService by lazy {GitService()  }
    }

}
