package com.example.myweather.screens;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.example.myweather.R;
import com.example.myweather.data.WeatherViewModel;
import com.example.myweather.data.pojo.day.Day;
import com.example.myweather.data.pojo.seven_days.SevenDays;
import com.example.myweather.utils.CalendarUtils;
import com.example.myweather.utils.ChartUtils;
import com.example.myweather.utils.MyLocationProvider;
import com.example.myweather.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

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
    private Toolbar toolbar;


    private boolean isWifiConnected;
    private boolean isMobileConnected;
    public static String lang;

    private WeatherViewModel viewModelOfWeather;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_lang_eng:
                Toast.makeText(this, "Eng", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mi_lang_ru:
                Toast.makeText(this, "Ru", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mi_units_metric:
                Toast.makeText(this, "Metric", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mi_units_standard:
                Toast.makeText(this, "Standard", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyLocationProvider.findLocation(MainActivity.this);
        setDates(new Date());

        // LiveData
        viewModelOfWeather = ViewModelProviders.of(this).get(WeatherViewModel.class);

        viewModelOfWeather.getLiveDataSevenDaysFromDB().observe(this, sevenDays -> {
            try {
                updateSevenDaysLayout(sevenDays);
            } catch (Exception e) {
                Log.v("SEVEN_DAYS_TEST", e.getMessage());
            }
        });
        viewModelOfWeather.getLiveDataThrowable().observe(this, throwable -> Toast.makeText(MainActivity.this, getString(R.string.toast_error) + throwable.getMessage(), Toast.LENGTH_SHORT).show());
        viewModelOfWeather.getLiveDataDayFromDB().observe(this, day -> {
            if (day != null) {
                try {
                    viewModelOfWeather.loadDataOfSevenDay(day.getCoord().getLat(), day.getCoord().getLon());
                    updateDayLayout(day);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchWeather.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                weekLayout.setVisibility(View.VISIBLE);
            } else {
                weekLayout.setVisibility(View.INVISIBLE);
            }
        });

        buttonFindNearby.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                checkConnectionState();
            }
            if (isMobileConnected || isWifiConnected) {
                viewModelOfWeather.loadDataOfDay(MyLocationProvider.getLatitude(), MyLocationProvider.getLongitude());
            } else {
                Toast.makeText(MainActivity.this, R.string.toast_no_connection, Toast.LENGTH_SHORT).show();
            }
        });


        buttonFindByCity.setOnClickListener(v -> {
            String nameOfCity = editTextNameOfCity.getText().toString();
            if (!nameOfCity.isEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    checkConnectionState();
                }
                if (isMobileConnected || isWifiConnected) {
                    try {
                        viewModelOfWeather.loadDataOfDay(nameOfCity);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, R.string.toast_city_not_found + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast_no_connection, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_input_city, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDayLayout(Day day) {
        Picasso.get().load(NetworkUtils.buildIconPath(day.getWeather().get(0).getIcon())).into(imageView);
        textViewFeelsLike.setText(String.format(Locale.getDefault(), "%.1f" + getString(R.string.value_of_temp_calcium), day.getMain().getFeelsLike()));
        textViewTemp.setText(String.format(Locale.getDefault(), "%.1f" + getString(R.string.value_of_temp_calcium), day.getMain().getTemp()));
        textViewDescription.setText(String.format("%s", day.getWeather().get(0).getDescription()));
        textViewNameOfCity.setText(String.format("%s", day.getName()));
        textViewLatitude.setText(String.format(Locale.getDefault(), "%.4f", day.getCoord().getLat()));
        textViewLongitude.setText(String.format(Locale.getDefault(), "%.4f", day.getCoord().getLon()));
        textViewPressure.setText(String.format(Locale.getDefault(), "%s " + getString(R.string.value_of_pressure_mm), day.getMain().getPressure()));
        textViewHumidity.setText(String.format(Locale.getDefault(), "%s %%", day.getMain().getHumidity()));
        String[] directions = getResources().getStringArray(R.array.directions_of_wind);
        textViewDirectionOfWind.setText(String.format("%s", directions[(int) (Math.round(day.getWind().getDeg() / 45) % 8)]));
        textViewSpeedOfWind.setText(String.format(Locale.getDefault(), "%.1f " + getString(R.string.value_of_speed_of_wind_meters_in_sec), day.getWind().getSpeed()));
    }

    private ViewGroup.LayoutParams getLayoutParamsForTempColumn(View v, double temp) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = ((int) temp) * 10;
        return layoutParams;
    }

    private void updateSevenDaysLayout(SevenDays d) {
        try {
            ChartUtils chartUtils = new ChartUtils(d);

            viewColumnDay1.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay1, chartUtils.getTempList().get(0)));
            viewColumnDay2.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay2, chartUtils.getTempList().get(1)));
            viewColumnDay3.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay3, chartUtils.getTempList().get(2)));
            viewColumnDay4.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay4, chartUtils.getTempList().get(3)));
            viewColumnDay5.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay5, chartUtils.getTempList().get(4)));
            viewColumnDay6.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay6, chartUtils.getTempList().get(5)));
            viewColumnDay7.setLayoutParams(getLayoutParamsForTempColumn(viewColumnDay7, chartUtils.getTempList().get(6)));
        } catch (Exception e) {
            Log.v("CHART_UTILS_TEST", e.getMessage());
            e.printStackTrace();
        }

        textViewTempDay1.setText(String.format(Locale.getDefault(), "%.00f°", d.getDaily().get(0).getTemp().getDay()));
        textViewTempDay2.setText(String.format(Locale.getDefault(), "%.0f°", d.getDaily().get(1).getTemp().getDay()));
        textViewTempDay3.setText(String.format(Locale.getDefault(), "%.0f°", d.getDaily().get(2).getTemp().getDay()));
        textViewTempDay4.setText(String.format(Locale.getDefault(), "%.0f°", d.getDaily().get(3).getTemp().getDay()));
        textViewTempDay5.setText(String.format(Locale.getDefault(), "%.0f°", d.getDaily().get(4).getTemp().getDay()));
        textViewTempDay6.setText(String.format(Locale.getDefault(), "%.0f°", d.getDaily().get(5).getTemp().getDay()));
        textViewTempDay7.setText(String.format(Locale.getDefault(), "%.0f°", d.getDaily().get(6).getTemp().getDay()));

        Picasso.get().load(NetworkUtils.buildIconPath(d.getDaily().get(0).getWeather().get(0).getIcon())).into(imageViewDay1);
        Picasso.get().load(NetworkUtils.buildIconPath(d.getDaily().get(1).getWeather().get(0).getIcon())).into(imageViewDay2);
        Picasso.get().load(NetworkUtils.buildIconPath(d.getDaily().get(2).getWeather().get(0).getIcon())).into(imageViewDay3);
        Picasso.get().load(NetworkUtils.buildIconPath(d.getDaily().get(3).getWeather().get(0).getIcon())).into(imageViewDay4);
        Picasso.get().load(NetworkUtils.buildIconPath(d.getDaily().get(4).getWeather().get(0).getIcon())).into(imageViewDay5);
        Picasso.get().load(NetworkUtils.buildIconPath(d.getDaily().get(5).getWeather().get(0).getIcon())).into(imageViewDay6);
        Picasso.get().load(NetworkUtils.buildIconPath(d.getDaily().get(6).getWeather().get(0).getIcon())).into(imageViewDay7);

    }

    private void setDates(Date date) {
        //Todo: [] Проверить, чтоб даты соответвовали температуре -> сместить на 1 день: даты или загрузку данных
        textViewDate1.setText(CalendarUtils.addDays(date, 1));
        textViewDate2.setText(CalendarUtils.addDays(date, 2));
        textViewDate3.setText(CalendarUtils.addDays(date, 3));
        textViewDate4.setText(CalendarUtils.addDays(date, 4));
        textViewDate5.setText(CalendarUtils.addDays(date, 5));
        textViewDate6.setText(CalendarUtils.addDays(date, 6));
        textViewDate7.setText(CalendarUtils.addDays(date, 7));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkConnectionState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        isWifiConnected = false;
        isMobileConnected = false;
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConnected |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConnected |= networkInfo.isConnected();
            }
        }
    }
}