package com.example.myweather.api;

import com.example.myweather.data.pojo.day.Day;
import com.example.myweather.data.pojo.seven_days.SevenDays;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.myweather.utils.NetworkUtils.*;

public interface ApiService {
    @GET(ENDPOINT_WEATHER)
    Observable<Day> getDayResponse(@Query(PARAMS_API_KEY) String ApiId, @Query(PARAMS_CITY) String nameOfCity, @Query(PARAMS_UNITS) String units);

    @GET(ENDPOINT_WEATHER)
    Observable<Day> getDayResponse(@Query(PARAMS_API_KEY) String ApiId, @Query(PARAMS_LAT) double lat, @Query(PARAMS_LON) double lon, @Query(PARAMS_UNITS) String units);

    @GET(ENDPOINT_ONE_CALL)
    Observable<SevenDays> getSevenDaysResponse(@Query(PARAMS_API_KEY) String ApiId, @Query(PARAMS_LAT) double lat, @Query(PARAMS_LON) double lon, @Query(PARAMS_EXCLUDE) String exclude, @Query(PARAMS_UNITS) String units);

}
