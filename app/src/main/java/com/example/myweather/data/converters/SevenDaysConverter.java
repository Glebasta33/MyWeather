package com.example.myweather.data.converters;

import androidx.room.TypeConverter;


import com.example.myweather.data.pojo.seven_days.Daily;
import com.example.myweather.data.pojo.seven_days.Temp;
import com.example.myweather.data.pojo.seven_days.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SevenDaysConverter {

    @TypeConverter
    public String listOfDailyToString(List<Daily> daily) {
        return new Gson().toJson(daily);
    }

    @TypeConverter
    public List<Daily> stringToListOfDaily(String dailyAsString) throws JSONException {
        JSONArray jsonArrayDaily = new JSONArray(dailyAsString);
        ArrayList<Daily> dailyArrayList = new ArrayList<>();
        for (int i = 0; i < 7; i++){

            JSONObject jsonObjectDaily = (JSONObject) jsonArrayDaily.get(i);
            Daily daily = new Daily();

            JSONObject jsonObjectTemp = jsonObjectDaily.getJSONObject("temp");
            Temp temp = new Gson().fromJson(String.valueOf(jsonObjectTemp), Temp.class);
            daily.setTemp(temp);

            JSONArray jsonArrayWeather = jsonObjectDaily.getJSONArray("weather");
            JSONObject jsonObjectWeather = (JSONObject) jsonArrayWeather.get(0);
            Weather w = new Weather();
            w.setId(jsonObjectWeather.getInt("id"));
            w.setIcon(jsonObjectWeather.getString("icon"));
            ArrayList<Weather> weathers = new ArrayList<>();
            weathers.add(w);
            daily.setWeather(weathers);
            dailyArrayList.add(daily);
        }
        return dailyArrayList;
    }

}
