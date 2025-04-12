package com.example.quicknews.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitInstance {

    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static NewsApiService apiService;

    public static NewsApiService getApiService() {
        if (apiService == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(NewsApiService.class);
        }

        return apiService;
    }
}
