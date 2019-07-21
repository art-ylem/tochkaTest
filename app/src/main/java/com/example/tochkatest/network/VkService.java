package com.example.tochkatest.network;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VkService {

    private static VkService mInstance;
    private static final String BASE_URL = "https://api.vk.com/method/";
    private Retrofit mRetrofit;

    private VkService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();//чтобы писалось в лог
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();//связывает с интернетом
                client.addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//из json в объект
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//для работы rx
                .client(client.build())//сборка и запуск
                .build();
    }

    public static VkService getInstance() {
        if (mInstance == null) {
            mInstance = new VkService();
        }
        return mInstance;
    }


    public Api getJSONApi() {
        return mRetrofit.create(Api.class);//привязываем Api.class (интерфейс) к Retrofit
    }

}
