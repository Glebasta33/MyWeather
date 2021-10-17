package com.example.myweather.data.converters;

import androidx.room.TypeConverter;

import com.example.myweather.data.pojo.day.Coord;
import com.google.gson.Gson;

public class DayConverter {
    @TypeConverter
    public String ObjectCoordToString(Coord coord) {
        return new Gson().toJson(coord);
    }

    @TypeConverter
    public Coord stringToObjectCoord(String coordAsString) {
        return new Gson().fromJson(coordAsString, Coord.class);
    }
}
