package com.example.myweather.utils;

import org.json.JSONException;
import org.json.JSONObject;


public class JSONUtils {

    public static final String KEY_COORDINATES_OBJECT = "coord";
    public static final String KEY_LON = "lon";
    public static final String KEY_LAT = "lat";

    public static final String KEY_WEATHER_ARR = "weather";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ICON = "icon";

    public static final String KEY_MAIN_OBJECT = "main";
    public static final String KEY_TEMP = "temp";
    public static final String KEY_TEMP_FEELS_LIKE = "feels_like";
    public static final String KEY_PRESSURE = "pressure";
    public static final String KEY_HUMIDITY = "humidity";

    public static final String KEY_WIND_OBJECT = "wind";
    public static final String KEY_SPEED_OF_WIND = "speed";
    public static final String KEY_DIRECTION_OF_WIND = "deg";

    public static final String KEY_NAME_OF_CITY = "name";

    public static Weather getWeatherFromJSON(JSONObject jsonObject) {
        Weather weather = null;
        try {
            String nameOfCity = jsonObject.getString(KEY_NAME_OF_CITY);
            JSONObject jsonObjectCoordinates = jsonObject.getJSONObject(KEY_COORDINATES_OBJECT);
            double lon = jsonObjectCoordinates.getDouble(KEY_LON);
            double lat = jsonObjectCoordinates.getDouble(KEY_LAT);
            String description = jsonObject.getJSONArray(KEY_WEATHER_ARR).getJSONObject(0).getString(KEY_DESCRIPTION);
            String icon = jsonObject.getJSONArray(KEY_WEATHER_ARR).getJSONObject(0).getString(KEY_ICON);
            JSONObject jsonObjectMain = jsonObject.getJSONObject(KEY_MAIN_OBJECT);
            double temp = jsonObjectMain.getDouble(KEY_TEMP);
            double tempFeelsLike = jsonObjectMain.getDouble(KEY_TEMP_FEELS_LIKE);
            double pressure = jsonObjectMain.getDouble(KEY_PRESSURE);
            double humidity = jsonObjectMain.getDouble(KEY_HUMIDITY);
            JSONObject jsonObjectWind = jsonObject.getJSONObject(KEY_WIND_OBJECT);
            double speedOfWind = jsonObjectWind.getDouble(KEY_SPEED_OF_WIND);
            double directionOfWind = jsonObjectWind.getDouble(KEY_DIRECTION_OF_WIND);
            weather = new Weather(lon, lat, description, temp, tempFeelsLike, pressure, humidity, speedOfWind, directionOfWind, nameOfCity, icon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
