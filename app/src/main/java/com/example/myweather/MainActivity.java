package com.example.myweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button buttonFindNearly;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTest = findViewById(R.id.textViewTest);
        editTextNameOfCity = findViewById(R.id.editTextNameOfCity);
        buttonFindNearly = findViewById(R.id.buttonFindNearly);
        MyLocationProvider.findLocation(MainActivity.this);

        buttonFindNearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }



    public void onClickFind(View view) {
        String nameOfCity = editTextNameOfCity.getText().toString();
        if (!nameOfCity.isEmpty()) {
           JSONObject jsonObject = NetworkUtils.getJSON(nameOfCity);
            weather = JSONUtils.getWeatherFromJSON(jsonObject);
            String weatherResult = String.format("Погода: %s\nТемпература: %s\nОщущается как: %s\nДавление: %s\nВлажность: %s\nСкорость вертра: %s\nНаправление: %s\n",
            weather.getDescription(), weather.getTemp(), weather.getTempFeelsLike(), weather.getPressure(), weather.getHumidity(), weather.getSpeedOfWind(), weather.getDirectionOfWind());
            textViewTest.setText(weatherResult);
        } else {
            Toast.makeText(this, "Введите название города", Toast.LENGTH_SHORT).show();
        }
    }

}