package com.example.myweather.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myweather.api.ApiFactory;
import com.example.myweather.api.ApiService;
import com.example.myweather.data.pojo.day.Day;
import com.example.myweather.data.pojo.seven_days.SevenDays;
import com.example.myweather.utils.ApiKeyStorage;

import java.util.concurrent.ExecutionException;

import static com.example.myweather.utils.NetworkUtils.*;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends AndroidViewModel {

    private static Database db;
    private LiveData<Day> liveDataDayFromDB;
    private LiveData<SevenDays> liveDataSevenDaysFromDB;
    private MutableLiveData<Day> liveDataDay;
    private MutableLiveData<SevenDays> liveDataSevenDays;
    private MutableLiveData<Throwable> liveDataThrowable;
    private ApiFactory apiFactory;
    private ApiService apiService;
    private Disposable disposableDay;
    private Disposable disposableSevenDays;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        liveDataDay = new MutableLiveData<>();
        liveDataSevenDays = new MutableLiveData<>();
        liveDataThrowable = new MutableLiveData<>();
        apiFactory = ApiFactory.getInstance();
        apiService = apiFactory.getApiService();
        db = Database.getInstance(application);
        liveDataDayFromDB = db.getDayDao().getDay();
        liveDataSevenDaysFromDB = db.getSevenDaysDao().getSevenDays();
    }

    public void loadDataOfDay(String nameOfCity) {
        disposableDay = apiService.getDayResponse(ApiKeyStorage.API_KEY, nameOfCity, VALUE_UNITS_METRIC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        day -> {
                            liveDataDay.setValue(day);
                            clear();
                            insert(day);
                        },
                        throwable -> liveDataThrowable.setValue(throwable)
                );
    }

    public void loadDataOfDay(double lat, double lon) {
        disposableDay = apiService.getDayResponse(ApiKeyStorage.API_KEY, lat, lon, VALUE_UNITS_METRIC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        day -> {
                            liveDataDay.setValue(day);
                            clear();
                            insert(day);
                        },
                        throwable -> liveDataThrowable.setValue(throwable)
                );
    }

    public void loadDataOfSevenDay(double lat, double lon) {
        disposableSevenDays = apiService.getSevenDaysResponse(ApiKeyStorage.API_KEY, lat, lon, EXCLUDED_DATA, VALUE_UNITS_METRIC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sevenDays -> {
                            liveDataSevenDays.setValue(sevenDays);
                            clearSevenDays();
                            insertIntoSevenDays(sevenDays);
                        },
                        throwable -> liveDataThrowable.setValue(throwable));
    }

    public LiveData<Day> getLiveDataDayFromDB() {
        return liveDataDayFromDB;
    }

    public LiveData<SevenDays> getLiveDataSevenDaysFromDB() {
        return liveDataSevenDaysFromDB;
    }

    public LiveData<Day> getLiveDataOfDay() {
        return liveDataDay;
    }

    public LiveData<SevenDays> getLiveDataOfSevenDays() {
        return liveDataSevenDays;
    }

    public LiveData<Throwable> getLiveDataThrowable() {
        return liveDataThrowable;
    }

    private long insert(Day day) throws ExecutionException, InterruptedException {
        return new InsertTask().execute(day).get();
    }

    private void clear() {
        new ClearTask().execute();
    }

    private void insertIntoSevenDays(SevenDays sevenDays) {
        new InsertSevenDaysTask().execute(sevenDays);
    }

    private void clearSevenDays() {
        new ClearSevenDaysTask().execute();
    }

    private static class InsertTask extends AsyncTask<Day, Void, Long> {
        @Override
        protected Long doInBackground(Day... days) {
            Long id = null;
            if (days != null && days.length > 0) {
                id = db.getDayDao().insertDay(days[0]);
            }
            return id;
        }
    }

    private static class ClearTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            db.getDayDao().clearDay();
            return null;
        }
    }

    private static class InsertSevenDaysTask extends AsyncTask<SevenDays, Void, Void> {
        @Override
        protected Void doInBackground(SevenDays... sevenDays) {
            if (sevenDays != null && sevenDays.length > 0) {
                db.getSevenDaysDao().insertSevenDays(sevenDays[0]);
            }
            return null;
        }
    }

    private static class ClearSevenDaysTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            db.getSevenDaysDao().clearSevenDays();
            return null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposableDay.dispose();
        disposableSevenDays.dispose();
    }
}
