package com.example.myweather.data.pojo.day;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DayDao {
    @Query("SELECT * FROM day")
    LiveData<Day> getDay();

    @Insert
    long insertDay(Day day);

    @Query("DELETE FROM day")
    void clearDay();
}
