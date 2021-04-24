package com.example.thovin;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static final String BASE_URL = "http://192.168.1.13:29321/v1/";
    public static final boolean DEBUG = true;

    private Retrofit retrofit;

    private static final HttpClient instance = new HttpClient();

    public HttpClient() {
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
        OkHttpClient client = getOkHttpClient().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Create an OkHttpClient
     * @return the http client
     */
    public OkHttpClient.Builder getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (DEBUG) okHttpClient.addInterceptor(getLoggingInterceptor());
        return okHttpClient;
    }

    /**
     * Return the logging interceptor, useful for debugging
     * @return the interceptor
     */
    public HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
