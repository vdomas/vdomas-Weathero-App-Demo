package com.gmail.vdomasapp.weathero.network;

import com.gmail.vdomasapp.weathero.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    public static Retrofit getRetrofit(String url) {

        //Logging interceptor for debugging api calls
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if (Constants.DEBUG) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

        //Handling network connection timeouts
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
