package com.example.myweather.data.pojo.seven_days;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SevenDaysDao {
    @Query("SELECT * FROM seven_days")
    LiveData<SevenDays> getSevenDays();

    @Insert
    void insertSevenDays(SevenDays sevenDays);

    @Query("DELETE FROM seven_days")
    void clearSevenDays();
}
