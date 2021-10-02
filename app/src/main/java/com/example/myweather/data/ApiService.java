package com.example.myweather.data;

import com.example.myweather.data.pojo.day.Day;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather")
    Observable<Day> getMain(@Query("appid") String ApiId, @Query("q") String nameOfCity);
}
