package com.example.myweather.utils;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class JSONUtils {
    public static final String KEY_WEATHER_ARR = "weather";
    public static final String KEY_DESCRIPTION = "description";

    public static final String KEY_MAIN_OBJECT = "main";
    public static final String KEY_TEMP = "temp";
    public static final String KEY_TEMP_FEELS_LIKE = "feels_like";
    public static final String KEY_PRESSURE = "pressure";
    public static final String KEY_HUMIDITY = "humidity";

    public static final String KEY_WIND_OBJECT = "wind";
    public static final String KEY_SPEED_OF_WIND = "speed";
    public static final String KEY_DIRECTION_OF_WIND = "deg";

    public static Weather getWeatherFromJSON(JSONObject jsonObject) {
        Weather weather = null;
        try {
            String description = jsonObject.getJSONArray(KEY_WEATHER_ARR).getJSONObject(0).getString(KEY_DESCRIPTION);
            JSONObject jsonObjectMain = jsonObject.getJSONObject(KEY_MAIN_OBJECT);
            double temp = jsonObjectMain.getDouble(KEY_TEMP);
            double tempFeelsLike = jsonObjectMain.getDouble(KEY_TEMP_FEELS_LIKE);
            double pressure = jsonObjectMain.getDouble(KEY_PRESSURE);
            double humidity = jsonObjectMain.getDouble(KEY_HUMIDITY);
            JSONObject jsonObjectWind = jsonObject.getJSONObject(KEY_WIND_OBJECT);
            double speedOfWind = jsonObjectWind.getDouble(KEY_SPEED_OF_WIND);
            double directionOfWind = jsonObjectWind.getDouble(KEY_DIRECTION_OF_WIND);
            weather = new Weather(description, temp, tempFeelsLike, pressure, humidity, speedOfWind, directionOfWind);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
