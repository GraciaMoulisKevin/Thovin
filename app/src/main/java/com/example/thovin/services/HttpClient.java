package com.example.thovin.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static final String BASE_URL = "http://192.168.1.13:29321/v1/";
    public static final boolean DEBUG = true;
    private static final HttpClient instance = new HttpClient();

    private static Retrofit retrofit;

    private HttpClient() {
        initHttpClient();
    }

    public static HttpClient getInstance() {
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * Initialise our http client (Retrofit)
     */
    public void initHttpClient() {
        if (DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }
}
