package com.example.myweather.data.pojo.day;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("1h")
    @Expose
    private int _1h;

    public int get1h() {
        return _1h;
    }

    public void set1h(int _1h) {
        this._1h = _1h;
    }
}
