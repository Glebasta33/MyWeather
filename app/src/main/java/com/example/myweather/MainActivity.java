package com.example.myweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweather.utils.JSONUtils;
import com.example.myweather.utils.NetworkUtils;
import com.example.myweather.utils.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTest;
    private EditText editTextNameOfCity;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTest = findViewById(R.id.textViewTest);
        editTextNameOfCity = findViewById(R.id.editTextNameOfCity);

        if (savedInstanceState != null) {
            textViewTest.setText(savedInstanceState.getString("description"));
            savedInstanceState.getDouble("temp");
            savedInstanceState.getDouble("tempFeelsLike");
            savedInstanceState.getDouble("pressure");
            savedInstanceState.getDouble("humidity");
            savedInstanceState.getDouble("speedOfWind");
            savedInstanceState.getDouble("directionOfWind");
        }
    }

    public void onClickFind(View view) {
        String nameOfCity = editTextNameOfCity.getText().toString();
        if (!nameOfCity.isEmpty()) {
           JSONObject jsonObject = NetworkUtils.getJSON(nameOfCity);
            weather = JSONUtils.getWeatherFromJSON(jsonObject);
            String weatherResult = String.format("Погода: %s\nТемпература: %s\nОщущается как: %s\nДавление: %s\nВлажность: %s\nСкорость вертра: %s\nНаправление: %s\n",
            weather.getDescription(), weather.getTemp(), weather.getTempFeelsLike(), weather.getPressure(), weather.getHumidity(), weather.getSpeedOfWind(), weather.getDirectionOfWind());
            textViewTest.setText(weatherResult);
           if (jsonObject == null) {
               Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
           } else {
               Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
           }
        } else {
            Toast.makeText(this, "Введите название города", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (weather != null) {
            outState.putString("description", weather.getDescription());
            outState.putDouble("temp", weather.getTemp());
            outState.putDouble("tempFeelsLike", weather.getTempFeelsLike());
            outState.putDouble("pressure", weather.getPressure());
            outState.putDouble("humidity", weather.getHumidity());
            outState.putDouble("speedOfWind", weather.getSpeedOfWind());
            outState.putDouble("directionOfWind", weather.getDirectionOfWind());
        }
    }
}