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
import com.example.myweather.utils.ApiKeyStorage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DayViewModel extends AndroidViewModel {

    private Disposable disposable;
    private MutableLiveData<Day> liveDataDay;
    private ApiFactory apiFactory;
    private ApiService apiService;

    public DayViewModel(@NonNull Application application) {
        super(application);
        liveDataDay = new MutableLiveData<>();
        apiFactory = ApiFactory.getInstance();
        apiService = apiFactory.getApiService();
    }

    public LiveData<Day> getLiveDataDay() {
        return liveDataDay;
    }

    public void loadData(String nameOfCity) {
        disposable = apiService.getMain(ApiKeyStorage.API_KEY, nameOfCity)
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

    public void loadData(double lat, double lon) {
        disposable = apiService.getMain(ApiKeyStorage.API_KEY, lat, lon)
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


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
