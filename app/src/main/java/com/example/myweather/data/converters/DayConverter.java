package com.example.myweather.data.converters;

import androidx.room.TypeConverter;

import com.example.myweather.data.pojo.day.Coord;
import com.example.myweather.data.pojo.day.Main;
import com.example.myweather.data.pojo.day.Weather;
import com.example.myweather.data.pojo.day.Wind;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DayConverter {
    @TypeConverter
    public String objectCoordToString(Coord coord) {
        return new Gson().toJson(coord);
    }

    @TypeConverter
    public Coord stringToObjectCoord(String coordAsString) {
        return new Gson().fromJson(coordAsString, Coord.class);
    }

    @TypeConverter
    public String listOfWeatherToString(List<Weather> weathers) {
        return new Gson().toJson(weathers);
    }

    @TypeConverter
    public List<Weather> stringToListOfWeather(String weatherAsString) throws JSONException {
        JSONArray jsonArray = new JSONArray(weatherAsString);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        Weather w = new Weather();
        w.setId(jsonObject.getInt("id"));
        w.setDescription(jsonObject.getString("description"));
        w.setIcon(jsonObject.getString("icon"));
        w.setMain(jsonObject.getString("main"));
        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(w);
        return weathers;
    }

    @TypeConverter
    public String objectMainToString(Main main) {
        return new Gson().toJson(main);
    }

    @TypeConverter
    public Main stringToObjectMain(String mainAsString) {
        return new Gson().fromJson(mainAsString, Main.class);
    }

    @TypeConverter
    public String objectWindToString(Wind wind) {
        return new Gson().toJson(wind);
    }

    @TypeConverter
    public Wind stringToObjectWind(String windAsString) {
        return new Gson().fromJson(windAsString, Wind.class);
    }
}
