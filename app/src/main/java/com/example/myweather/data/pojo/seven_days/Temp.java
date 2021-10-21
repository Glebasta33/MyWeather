package com.example.myweather.data.pojo.seven_days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp {

    @SerializedName("day")
    @Expose
    private double day;

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

}
