package com.example.thovin;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    public static final boolean DEBUG_OFF = false;
    public static final boolean DEBUG_ON = true;

    private final String BASE_URL;
    private Retrofit retrofit;
    private final Boolean debug;

    public HttpClient(String BASE_URL) {
        this.BASE_URL = BASE_URL;
        this.debug = true;
        initHttpClient();
    }

    public HttpClient(String BASE_URL, Boolean debug) {
        this.BASE_URL = BASE_URL;
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
