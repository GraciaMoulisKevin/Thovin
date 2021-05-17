package com.example.thovin.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    /**
     * The base url to make call at the API.
     * On user device purpose, please use your IP address else use the emulator line
     */
    // private static final String BASE_URL = "http://192.168.1.13:29321/v1/"; // personal device
    private static final String BASE_URL = "http://10.0.2.2:29321/v1/"; // emulator


    /**
     * Define debug output
     */
    public static final boolean DEBUG = true;


    private static HttpClient INSTANCE;
    private static Retrofit retrofit;


    // --- All services provided
    private static IAuthServices IAuthServices;
    private static IRestaurantServices IRestaurantServices;
    private static ICartServices ICartServices;
    private static IOrderServices IOrderServices;


    private HttpClient() {
        initHttpClient();
    }

    /**
     * Get the current instance of this HttpClient
     * @return The instance
     */
    public static HttpClient getInstance() {
        if (INSTANCE == null) INSTANCE = new HttpClient();
        return INSTANCE;
    }

    /**
     * Get a retrofit instance of authentication services
     * @return The retrofit authentication service instance
     */
    public IAuthServices getAuthServices() {
        if (IAuthServices == null) IAuthServices = retrofit.create(IAuthServices.class);
        return IAuthServices;
    }

    /**
     * Get a retrofit instance of restaurant services
     * @return The retrofit restaurant service instance
     */
    public IRestaurantServices getRestaurantServices() {
        if (IRestaurantServices == null) IRestaurantServices = retrofit.create(IRestaurantServices.class);
        return IRestaurantServices;
    }

    /**
     * Get a retrofit instance of cart services
     * @return The retrofit cart service instance
     */
    public ICartServices getCartServices() {
        if (ICartServices == null) ICartServices = retrofit.create(ICartServices.class);
        return ICartServices;
    }

    /**
     * Get a retrofit instance of orders services
     * @return The retrofit cart service instance
     */
    public IOrderServices getOrderServices() {
        if (IOrderServices == null) IOrderServices = retrofit.create(IOrderServices.class);
        return IOrderServices;
    }

    /**
     * Initialise a Retrofit client
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
