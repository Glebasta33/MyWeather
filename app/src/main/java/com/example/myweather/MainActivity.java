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

    private EditText editTextNameOfCity;
    private Button buttonFindByCity;
    private Button buttonFindNearly;
    private ConstraintLayout weekLayout;
    private Switch switchWeather;
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

    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNameOfCity = findViewById(R.id.editTextNameOfCity);
        buttonFindByCity = findViewById(R.id.buttonFindByCity);
        buttonFindNearly = findViewById(R.id.buttonFindNearly);
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
                updateUiData(weather);
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
                        updateUiData(weather);
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

    private void updateUiData(Weather w) {
        Bitmap bitmap = NetworkUtils.getIcon(w);
        imageView.setImageBitmap(bitmap);
        textViewTemp.setText(String.format("%s°C", w.getTemp()));
        textViewDescription.setText(String.format("%s", w.getDescription()));
        textViewNameOfCity.setText(String.format("%s", w.getNameOfCity()));
        textViewLatitude.setText(String.format("%s", w.getLat()));
        textViewLongitude.setText(String.format("%s", w.getLon()));
        textViewFeelsLike.setText(String.format("%s°C", w.getTempFeelsLike()));
        textViewPressure.setText(String.format("%s", w.getPressure()));
        textViewHumidity.setText(String.format("%s", w.getHumidity()));
        textViewDirectionOfWind.setText(String.format("%s", w.getDirectionOfWind()));
        textViewSpeedOfWind.setText(String.format("%s м/с", w.getSpeedOfWind()));
    }

}