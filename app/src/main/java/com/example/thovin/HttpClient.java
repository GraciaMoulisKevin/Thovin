package com.example.thovin;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static final String BASE_URL = "http://192.168.1.13:29321/v1/";
    public static final boolean DEBUG_OFF = false;
    public static final boolean DEBUG_ON = true;

    private Retrofit retrofit;
    private final Boolean debug;

    public HttpClient() {
        this.debug = true;
        initHttpClient();
    }

    public HttpClient(Boolean debug) {
        this.debug = debug;
        initHttpClient();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void initHttpClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    public OkHttpClient.Builder getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (debug) return okHttpClient.addInterceptor(getLoggingInterceptor());
        else return okHttpClient;
    }


}
