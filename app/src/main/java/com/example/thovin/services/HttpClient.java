package com.example.thovin.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    // On personal mobile (use personal IP address)
    private static final String BASE_URL = "http://192.168.1.13:29321/v1/";

    // On emulator
    // private static final String BASE_URL = "http://10.0.2.2:29321/v1/";
    public static final boolean DEBUG = true;

    private static HttpClient INSTANCE;
    private static Retrofit retrofit;

    // --- Services
    private static AuthServices authServices;

    private HttpClient() {
        initHttpClient();
    }

    public static HttpClient getInstance() {
        if (INSTANCE == null) INSTANCE = new HttpClient();
        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public AuthServices getAuthServices() {
        if (authServices == null) authServices = retrofit.create(AuthServices.class);
        return authServices;
    }

    /**
     * Initialise our http client (Retrofit)
     */
    public void initHttpClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if (DEBUG) okHttpBuilder.addInterceptor(getInterceptor()).build();

        OkHttpClient client = okHttpBuilder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * Return the logging interceptor, useful for debugging
     * @return the interceptor
     */
    public HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
