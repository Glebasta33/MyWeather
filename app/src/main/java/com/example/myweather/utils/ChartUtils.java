package com.example.myweather.utils;

import android.os.Build;

import com.example.myweather.data.pojo.seven_days.SevenDays;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChartUtils {
    private double min;
    private double minAbs;
    private ArrayList<Double> tempList;
    private ArrayList<Double> normalizedTempListBelowZero;

    public ChartUtils(SevenDays sevenDays) {
        tempList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            tempList.add((sevenDays.getDaily().get(i).getTemp().getDay()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ArrayList<Double> arrayList = tempList;
            List<Double> sorted = arrayList.stream()
                    .sorted()
                    .collect(Collectors.toList());
            min = sorted.get(0);
            minAbs = Math.abs(min);
        }

        normalizedTempListBelowZero = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            double temp = tempList.get(i);
            double newTemp = temp + minAbs + 1;
            normalizedTempListBelowZero.add(newTemp);
        }


    }

    public ArrayList<Double> getTempList() {
        if (min < 0) {
            return normalizedTempListBelowZero;
        } else {
            return tempList;
        }
    }
}
