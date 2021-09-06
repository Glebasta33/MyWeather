package com.example.myweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweather.utils.JSONUtils;
import com.example.myweather.utils.MyLocationProvider;
import com.example.myweather.utils.NetworkUtils;
import com.example.myweather.utils.Weather;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTest;
    private EditText editTextNameOfCity;
    private Button buttonFindByCity;
    private Button buttonFindNearly;
    private ConstraintLayout dayLayout;
    private ConstraintLayout weekLayout;
    private Switch switchWeather;
    private Weather weather;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTest = findViewById(R.id.textViewTest);
        editTextNameOfCity = findViewById(R.id.editTextNameOfCity);
        buttonFindByCity = findViewById(R.id.buttonFindByCity);
        buttonFindNearly = findViewById(R.id.buttonFindNearly);
        dayLayout = findViewById(R.id.dayLayout);
        weekLayout = findViewById(R.id.weekLayout);
        switchWeather = findViewById(R.id.switchDayOrWeek);
        imageView = findViewById(R.id.imageView);

        MyLocationProvider.findLocation(MainActivity.this);

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

        buttonFindNearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = NetworkUtils.getJSON(MyLocationProvider.getLatitude(), MyLocationProvider.getLongitude());
                weather = JSONUtils.getWeatherFromJSON(jsonObject);
                String weatherResult = String.format("Город: %s\nШирота: %s\nДолгота: %s\nПогода: %s\nТемпература: %s°С\nОщущается как: %s°С\nДавление: %s\nВлажность: %s\nСкорость вертра: %s\nНаправление: %s\nIcon: %s",
                        weather.getNameOfCity(), weather.getLat(), weather.getLon(), weather.getDescription(), weather.getTemp(), weather.getTempFeelsLike(), weather.getPressure(), weather.getHumidity(), weather.getSpeedOfWind(), weather.getDirectionOfWind(), weather.getIcon());
                Bitmap bitmap = NetworkUtils.getIcon(weather);
                textViewTest.setText(weatherResult);
                imageView.setImageBitmap(bitmap);
            }
        });

        buttonFindByCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfCity = editTextNameOfCity.getText().toString();
                if (!nameOfCity.isEmpty()) {
                    JSONObject jsonObject = NetworkUtils.getJSON(nameOfCity);
                    try {
                        weather = JSONUtils.getWeatherFromJSON(jsonObject);
                        String weatherResult = String.format("Город: %s\nШирота: %s\nДолгота: %s\nПогода: %s\nТемпература: %s°С\nОщущается как: %s°С\nДавление: %s\nВлажность: %s\nСкорость вертра: %s\nНаправление: %s\nIcon: %s",
                                weather.getNameOfCity(), weather.getLat(), weather.getLon(), weather.getDescription(), weather.getTemp(), weather.getTempFeelsLike(), weather.getPressure(), weather.getHumidity(), weather.getSpeedOfWind(), weather.getDirectionOfWind(), weather.getIcon());
                        textViewTest.setText(weatherResult);
                        Bitmap bitmap = NetworkUtils.getIcon(weather);
                        textViewTest.setText(weatherResult);
                        imageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Некорректное название города", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Введите название города", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}