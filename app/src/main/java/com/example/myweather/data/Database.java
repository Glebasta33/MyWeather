package com.example.myweather.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myweather.data.pojo.day.Day;
import com.example.myweather.data.pojo.day.DayDao;
import com.example.myweather.data.pojo.seven_days.SevenDays;
import com.example.myweather.data.pojo.seven_days.SevenDaysDao;

@androidx.room.Database(entities = {Day.class, SevenDays.class}, version = 16, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static Database db;
    public static final String DB_NAME = "weather.db";
    public static final Object LOCK = new Object();

    public static Database getInstance(Context context) {
        synchronized (LOCK) {
            if (db == null) {
                db = Room.databaseBuilder(context, Database.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return db;
    }

    public abstract DayDao getDayDao();

    public abstract SevenDaysDao getSevenDaysDao();
}
