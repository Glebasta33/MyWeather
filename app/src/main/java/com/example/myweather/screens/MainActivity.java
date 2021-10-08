package com.example.myweather.screens;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweather.R;
import com.example.myweather.data.pojo.day.Day;
import com.example.myweather.utils.CalendarUtils;
import com.example.myweather.utils.MyLocationProvider;
import com.example.myweather.utils.NetworkUtils;
import com.example.myweather.utils.Weather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // general layout
    private EditText editTextNameOfCity;
    private Button buttonFindByCity;
    private Button buttonFindNearby;
    private ConstraintLayout weekLayout;
    private Switch switchWeather;

    // layout of current day
    private ImageView imageView;
    private TextView textViewTemp;
    private TextView textViewDescription;
    private TextView textViewNameOfCity;
    private TextView textViewLatitude;
    private TextView textViewLongitude;
    private TextView textViewFeelsLike;
    private TextView textViewPressure;
    private TextView textViewHumidity;
    private TextView textViewDirectionOfWind;
    private TextView textViewSpeedOfWind;

    // layout of next 7 days
    private View viewColumnDay1;
    private View viewColumnDay2;
    private View viewColumnDay3;
    private View viewColumnDay4;
    private View viewColumnDay5;
    private View viewColumnDay6;
    private View viewColumnDay7;
    private TextView textViewTempDay1;
    private TextView textViewTempDay2;
    private TextView textViewTempDay3;
    private TextView textViewTempDay4;
    private TextView textViewTempDay5;
    private TextView textViewTempDay6;
    private TextView textViewTempDay7;
    private ImageView imageViewDay1;
    private ImageView imageViewDay2;
    private ImageView imageViewDay3;
    private ImageView imageViewDay4;
    private ImageView imageViewDay5;
    private ImageView imageViewDay6;
    private ImageView imageViewDay7;
    private TextView textViewDate1;
    private TextView textViewDate2;
    private TextView textViewDate3;
    private TextView textViewDate4;
    private TextView textViewDate5;
    private TextView textViewDate6;
    private TextView textViewDate7;

    private SharedPreferences preferences;
    private Weather currentWeather;
    private boolean isWifiConn;
    private boolean isMobileConn;
    public static String lang;

    private DayViewModel viewModelOfDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lang = Locale.getDefault().getLanguage();

        editTextNameOfCity = findViewById(R.id.editTextNameOfCity);
        buttonFindByCity = findViewById(R.id.buttonFindByCity);
        buttonFindNearby = findViewById(R.id.buttonFindNearby);
        weekLayout = findViewById(R.id.weekLayout);
        switchWeather = findViewById(R.id.switchDayOrWeek);

        imageView = findViewById(R.id.imageView);
        textViewTemp = findViewById(R.id.textViewTempValue);
        textViewDescription = findViewById(R.id.textViewDescriptionValue);
        textViewNameOfCity = findViewById(R.id.textViewNameOfCityValue);
        textViewLatitude = findViewById(R.id.textViewLatitudeValue);
        textViewLongitude = findViewById(R.id.textViewLongitudeValue);
        textViewFeelsLike = findViewById(R.id.textViewFeelsLikeValue);
        textViewPressure = findViewById(R.id.textViewPressureValue);
        textViewHumidity = findViewById(R.id.textViewHumidityValue);
        textViewDirectionOfWind = findViewById(R.id.textViewDirectionOfWindValue);
        textViewSpeedOfWind = findViewById(R.id.textViewSpeedOfWindValue);

        viewColumnDay1 = findViewById(R.id.viewColumnDay1);
        viewColumnDay2 = findViewById(R.id.viewColumnDay2);
        viewColumnDay3 = findViewById(R.id.viewColumnDay3);
        viewColumnDay4 = findViewById(R.id.viewColumnDay4);
        viewColumnDay5 = findViewById(R.id.viewColumnDay5);
        viewColumnDay6 = findViewById(R.id.viewColumnDay6);
        viewColumnDay7 = findViewById(R.id.viewColumnDay7);
        textViewTempDay1 = findViewById(R.id.textViewTempDay1);
        textViewTempDay2 = findViewById(R.id.textViewTempDay2);
        textViewTempDay3 = findViewById(R.id.textViewTempDay3);
        textViewTempDay4 = findViewById(R.id.textViewTempDay4);
        textViewTempDay5 = findViewById(R.id.textViewTempDay5);
        textViewTempDay6 = findViewById(R.id.textViewTempDay6);
        textViewTempDay7 = findViewById(R.id.textViewTempDay7);
        imageViewDay1 = findViewById(R.id.imageViewDay1);
        imageViewDay2 = findViewById(R.id.imageViewDay2);
        imageViewDay3 = findViewById(R.id.imageViewDay3);
        imageViewDay4 = findViewById(R.id.imageViewDay4);
        imageViewDay5 = findViewById(R.id.imageViewDay5);
        imageViewDay6 = findViewById(R.id.imageViewDay6);
        imageViewDay7 = findViewById(R.id.imageViewDay7);
        textViewDate1 = findViewById(R.id.textViewDate1);
        textViewDate2 = findViewById(R.id.textViewDate2);
        textViewDate3 = findViewById(R.id.textViewDate3);
        textViewDate4 = findViewById(R.id.textViewDay4);
        textViewDate5 = findViewById(R.id.textViewDay5);
        textViewDate6 = findViewById(R.id.textViewDay6);
        textViewDate7 = findViewById(R.id.textViewDay7);

        MyLocationProvider.findLocation(MainActivity.this);

        currentWeather = new Weather();

//        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        if (preferences.contains("description")) {
//            currentWeather.setLat((double) preferences.getFloat("lat", 0));
//            currentWeather.setLon((double) preferences.getFloat("lon", 0));
//            currentWeather.setDescription(preferences.getString("description", " "));
//            currentWeather.setTemp((double) preferences.getFloat("temp", 0));
//            currentWeather.setTempFeelsLike((double) preferences.getFloat("tempFeelsLike", 0));
//            currentWeather.setPressure((double) preferences.getFloat("pressure", 0));
//            currentWeather.setHumidity((double) preferences.getFloat("humidity", 0));
//            currentWeather.setSpeedOfWind((double) preferences.getFloat("speedOfWind", 0));
//            currentWeather.setDirectionOfWind((double) preferences.getFloat("directionOfWind", 0));
//            currentWeather.setNameOfCity(preferences.getString("nameOfCity", " "));
//            currentWeather.setIcon(preferences.getString("icon", " "));
//            updateDayLayout(currentWeather);
//        }
//
//        if (savedInstanceState != null) {
//            units = savedInstanceState.getString("units");
//            currentWeather.setLat(savedInstanceState.getDouble("lat"));
//            currentWeather.setLon(savedInstanceState.getDouble("lon"));
//            currentWeather.setDescription(savedInstanceState.getString("description"));
//            currentWeather.setTemp(savedInstanceState.getDouble("temp"));
//            currentWeather.setTempFeelsLike(savedInstanceState.getDouble("tempFeelsLike"));
//            currentWeather.setPressure(savedInstanceState.getDouble("pressure"));
//            currentWeather.setHumidity(savedInstanceState.getDouble("humidity"));
//            currentWeather.setSpeedOfWind(savedInstanceState.getDouble("speedOfWind"));
//            currentWeather.setDirectionOfWind(savedInstanceState.getDouble("directionOfWind"));
//            currentWeather.setNameOfCity(savedInstanceState.getString("nameOfCity"));
//            currentWeather.setIcon(savedInstanceState.getString("icon"));
//            updateDayLayout(currentWeather);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                checkConnectionState();
//            }
//            if (isMobileConn || isWifiConn) {
//                ArrayList<Weather> days = NetworkUtils.getArrayOfWeather(currentWeather.getLat(), currentWeather.getLon(), lang, units);
//                setNextSevenDaysLayout(days);
//            }
//        }

        setDates(new Date());

        // LiveData
        viewModelOfDay = ViewModelProviders.of(this).get(DayViewModel.class);
        viewModelOfDay.getLiveDataDay().observe(this, new Observer<Day>() {
            @Override
            public void onChanged(Day day) {
                Toast.makeText(MainActivity.this, "Город: " + day.getName() + "\nТемпература: " + day.getMain().getTemp(), Toast.LENGTH_SHORT).show();
            }
        });

        switchWeather.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weekLayout.setVisibility(View.VISIBLE);
                } else {
                    weekLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonFindNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    checkConnectionState();
                }
                if (isMobileConn || isWifiConn) {
                    viewModelOfDay.loadData(MyLocationProvider.getLatitude(), MyLocationProvider.getLongitude());
                    //Todo: [] Посмотреть код, связанный с получением координат
                    Log.i("coords", "coords: " + MyLocationProvider.getLatitude() + MyLocationProvider.getLongitude());
                    Toast.makeText(MainActivity.this, "coords: " + MyLocationProvider.getLatitude() + MyLocationProvider.getLongitude(), Toast.LENGTH_SHORT).show();
//                    currentWeather = NetworkUtils.getWeatherOfCurrentDay(MyLocationProvider.getLatitude(), MyLocationProvider.getLongitude(), lang, units);
//                    updateDayLayout(currentWeather);
//                    ArrayList<Weather> days = NetworkUtils.getArrayOfWeather(MyLocationProvider.getLatitude(), MyLocationProvider.getLongitude(), lang, units);
//                    setNextSevenDaysLayout(days);
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast_no_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonFindByCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfCity = editTextNameOfCity.getText().toString();
                if (!nameOfCity.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkConnectionState();
                    }
                    if (isMobileConn || isWifiConn) {
                        try {
                            viewModelOfDay.loadData(nameOfCity);
//                            currentWeather = NetworkUtils.getWeatherOfCurrentDay(nameOfCity, lang, units);
//                            updateDayLayout(currentWeather);
//                            ArrayList<Weather> days = NetworkUtils.getArrayOfWeather(currentWeather.getLat(), currentWeather.getLon(), lang, units);
//                            setNextSevenDaysLayout(days);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, R.string.toast_city_not_found, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, R.string.toast_no_connection, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.toast_input_city, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Todo: [] Закомментировать и свернуть методы, которые пока не нужны и будут мешаться \/

    private void updateDayLayout(Weather w) {
        Picasso.get().load(w.getIconPath()).into(imageView);
        textViewFeelsLike.setText(String.format(Locale.getDefault(), "%.1f" + getString(R.string.value_of_temp_calcium), w.getTempFeelsLike()));
        textViewTemp.setText(String.format(Locale.getDefault(), "%.1f" + getString(R.string.value_of_temp_calcium), w.getTemp()));
        textViewDescription.setText(String.format("%s", w.getDescription()));
        textViewNameOfCity.setText(String.format("%s", w.getNameOfCity()));
        textViewLatitude.setText(String.format(Locale.getDefault(), "%.4f", w.getLat()));
        textViewLongitude.setText(String.format(Locale.getDefault(), "%.4f", w.getLon()));
        textViewPressure.setText(String.format(Locale.getDefault(), "%.1f " + getString(R.string.value_of_pressure_mm), w.getPressure()));
        textViewHumidity.setText(String.format(Locale.getDefault(), "%.1f %%", w.getHumidity()));
        String[] directions = getResources().getStringArray(R.array.directions_of_wind);
        textViewDirectionOfWind.setText(String.format("%s", directions[w.getIndexOfDirectionsArray()]));
        textViewSpeedOfWind.setText(String.format(Locale.getDefault(), "%.1f " + getString(R.string.value_of_speed_of_wind_meters_in_sec), w.getSpeedOfWind()));
    }

    private ViewGroup.LayoutParams getLayoutParamsForTempColumn(View v, double temp) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = (int) temp * 15;
        return layoutParams;
    }

    private void setNextSevenDaysLayout(ArrayList<Weather> days) {
        Weather day1 = days.get(0);
        Weather day2 = days.get(1);
        Weather day3 = days.get(2);
        Weather day4 = days.get(3);
        Weather day5 = days.get(4);
        Weather day6 = days.get(5);
        Weather day7 = days.get(6);

        viewColumnDay1.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay1, day1.getTemp()));
        viewColumnDay2.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay2, day2.getTemp()));
        viewColumnDay3.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay3, day3.getTemp()));
        viewColumnDay4.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay4, day4.getTemp()));
        viewColumnDay5.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay5, day5.getTemp()));
        viewColumnDay6.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay6, day6.getTemp()));
        viewColumnDay7.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay7, day7.getTemp()));

        textViewTempDay1.setText(String.format(Locale.getDefault(), "%.0f°", day1.getTemp()));
        textViewTempDay2.setText(String.format(Locale.getDefault(), "%.0f°", day2.getTemp()));
        textViewTempDay3.setText(String.format(Locale.getDefault(), "%.0f°", day3.getTemp()));
        textViewTempDay4.setText(String.format(Locale.getDefault(), "%.0f°", day4.getTemp()));
        textViewTempDay5.setText(String.format(Locale.getDefault(), "%.0f°", day5.getTemp()));
        textViewTempDay6.setText(String.format(Locale.getDefault(), "%.0f°", day6.getTemp()));
        textViewTempDay7.setText(String.format(Locale.getDefault(), "%.0f°", day7.getTemp()));

        Picasso.get().load(day1.getIconPath()).into(imageViewDay1);
        Picasso.get().load(day2.getIconPath()).into(imageViewDay2);
        Picasso.get().load(day3.getIconPath()).into(imageViewDay3);
        Picasso.get().load(day4.getIconPath()).into(imageViewDay4);
        Picasso.get().load(day5.getIconPath()).into(imageViewDay5);
        Picasso.get().load(day6.getIconPath()).into(imageViewDay6);
        Picasso.get().load(day7.getIconPath()).into(imageViewDay7);

    }

    private void setDates(Date date) {
        textViewDate1.setText(CalendarUtils.addDays(date, 1));
        textViewDate2.setText(CalendarUtils.addDays(date, 2));
        textViewDate3.setText(CalendarUtils.addDays(date, 3));
        textViewDate4.setText(CalendarUtils.addDays(date, 4));
        textViewDate5.setText(CalendarUtils.addDays(date, 5));
        textViewDate6.setText(CalendarUtils.addDays(date, 6));
        textViewDate7.setText(CalendarUtils.addDays(date, 7));
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putDouble("lat", currentWeather.getLat());
//        outState.putDouble("lon", currentWeather.getLon());
//        outState.putString("description", currentWeather.getDescription());
//        outState.putDouble("temp", currentWeather.getTemp());
//        outState.putDouble("tempFeelsLike", currentWeather.getTempFeelsLike());
//        outState.putDouble("pressure", currentWeather.getPressure());
//        outState.putDouble("humidity", currentWeather.getHumidity());
//        outState.putDouble("speedOfWind", currentWeather.getSpeedOfWind());
//        outState.putDouble("directionOfWind", currentWeather.getDirectionOfWind());
//        outState.putString("nameOfCity", currentWeather.getNameOfCity());
//        outState.putString("icon", currentWeather.getIcon());
//        outState.putString("units", units);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        preferences.edit().putFloat("lat", (float) currentWeather.getLat()).apply();
//        preferences.edit().putFloat("lon", (float) currentWeather.getLon()).apply();
//        preferences.edit().putString("description", currentWeather.getDescription()).apply();
//        preferences.edit().putFloat("temp", (float) currentWeather.getTemp()).apply();
//        preferences.edit().putFloat("tempFeelsLike", (float) currentWeather.getTempFeelsLike()).apply();
//        preferences.edit().putFloat("pressure", (float) currentWeather.getPressure()).apply();
//        preferences.edit().putFloat("humidity", (float) currentWeather.getHumidity()).apply();
//        preferences.edit().putFloat("speedOfWind", (float) currentWeather.getSpeedOfWind()).apply();
//        preferences.edit().putFloat("directionOfWind", (float) currentWeather.getDirectionOfWind()).apply();
//        preferences.edit().putString("nameOfCity", currentWeather.getNameOfCity()).apply();
//        preferences.edit().putString("icon", currentWeather.getIcon()).apply();
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkConnectionState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        isWifiConn = false;
        isMobileConn = false;
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
    }
}