package com.example.myweather.api;

import com.example.myweather.data.pojo.day.Day;
import com.example.myweather.data.pojo.seven_days.SevenDays;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather")
    Observable<Day> getDayResponse(@Query("appid") String ApiId, @Query("q") String nameOfCity, @Query("units") String units);

    @GET("weather")
    Observable<Day> getDayResponse(@Query("appid") String ApiId, @Query("lat") double lat, @Query("lon") double lon, @Query("units") String units);

    @GET("onecall")
    Observable<SevenDays> getSevenDaysResponse(@Query("appid") String ApiId, @Query("lat") double lat, @Query("lon") double lon, @Query("exclude") String exclude, @Query("units") String units);

}
