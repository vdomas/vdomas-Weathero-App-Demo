package com.gmail.vdomasapp.weathero.network;


import com.gmail.vdomasapp.weathero.model.currentweather.CurrentWeather;
import com.gmail.vdomasapp.weathero.model.dailyweather.DailyWeather;
import com.gmail.vdomasapp.weathero.model.locationaddress.HereGeoCoder;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("data/2.5/weather")
    Single<CurrentWeather> getCurrentWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("lang") String language,
            @Query("appid") String apiKey,
            @Query("units") String unit
    );

    @GET("data/2.5/forecast/daily")
    Single<DailyWeather> getDailyWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("lang") String language,
            @Query("appid") String apiKey,
            @Query("units") String unit
    );

    @GET("6.2/reversegeocode.json")
    Single<HereGeoCoder> getCityName(
            @Query("app_id") String appId,
            @Query("app_code") String appCode,
            @Query("mode") String geoCoderMode,
            @Query("prox") String coordinates
    );
}
