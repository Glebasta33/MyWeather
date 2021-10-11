package com.example.myweather.screens;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myweather.api.ApiFactory;
import com.example.myweather.api.ApiService;
import com.example.myweather.data.pojo.day.Day;
import com.example.myweather.data.pojo.seven_days.SevenDays;
import com.example.myweather.utils.ApiKeyStorage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends AndroidViewModel {

    private Disposable disposableDay;
    private Disposable disposableSevenDays;
    private MutableLiveData<Day> liveDataDay;
    private MutableLiveData<SevenDays> liveDataSevenDays;
    private ApiFactory apiFactory;
    private ApiService apiService;
    private Day d;
    public static final String EXCLUDED_DATA = "current,hourly,minutely";

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        liveDataDay = new MutableLiveData<>();
        liveDataSevenDays = new MutableLiveData<>();
        apiFactory = ApiFactory.getInstance();
        apiService = apiFactory.getApiService();
    }

    public LiveData<Day> getLiveDataOfDay() {
        return liveDataDay;
    }

    public LiveData<SevenDays> getLiveDataOfSevenDays() {
        return liveDataSevenDays;
    }

    public void loadDataOfDay(String nameOfCity) {
        disposableDay = apiService.getDayResponse(ApiKeyStorage.API_KEY, nameOfCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Day>() {
                               @Override
                               public void accept(Day day) throws Exception {
                                   liveDataDay.setValue(day);
                                   d = day;
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   //Todo: [] Сделать liveDataExceptions
                               }
                           }
                );
    }

    public void loadDataOfDay(double lat, double lon) {
        disposableDay = apiService.getDayResponse(ApiKeyStorage.API_KEY, lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Day>() {
                               @Override
                               public void accept(Day day) throws Exception {
                                   liveDataDay.setValue(day);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.i("qwerty", throwable.getLocalizedMessage());
                               }
                           }
                );
    }

    public void loadDataOfSevenDay(double lat, double lon) {
        disposableSevenDays = apiService.getSevenDaysResponse(ApiKeyStorage.API_KEY, lat, lon, EXCLUDED_DATA)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SevenDays>() {
                    @Override
                    public void accept(SevenDays sevenDays) throws Exception {
                        liveDataSevenDays.setValue(sevenDays);
                        Log.i("7_days", sevenDays.getTimezone() + " ");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("7_days", throwable.getMessage() + " ");
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposableDay.dispose();
        disposableSevenDays.dispose();
    }
}
