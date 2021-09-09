package com.example.myweather;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweather.utils.CalendarUtils;
import com.example.myweather.utils.JSONUtils;
import com.example.myweather.utils.MyLocationProvider;
import com.example.myweather.utils.NetworkUtils;
import com.example.myweather.utils.Weather;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // general layout
    private EditText editTextNameOfCity;
    private Button buttonFindByCity;
    private Button buttonFindNearly;
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

    private Date date;

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

        date = new Date();
        setDates();

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
                updateDayLayout(getWeatherOfCurrentDay(MyLocationProvider.getLatitude(), MyLocationProvider.getLongitude()));
                ArrayList<Weather> days = getArrayOfWeather(MyLocationProvider.getLatitude(), MyLocationProvider.getLongitude());
                setNextSevenDaysLayout(days);
            }
        });


        buttonFindByCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfCity = editTextNameOfCity.getText().toString();
                if (!nameOfCity.isEmpty()) {
                    try {
                        Weather weather = getWeatherOfCurrentDay(nameOfCity);
                        updateDayLayout(weather);
                        ArrayList<Weather> days = getArrayOfWeather(weather.getLat(), weather.getLon());
                        setNextSevenDaysLayout(days);
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

    private void updateDayLayout(Weather w) {
        Picasso.get().load(w.getIconPath()).into(imageView);
        textViewTemp.setText(String.format("%s°C", w.getTemp()));
        textViewDescription.setText(String.format("%s", w.getDescription()));
        textViewNameOfCity.setText(String.format("%s", w.getNameOfCity()));
        textViewLatitude.setText(String.format("%s", w.getLat()));
        textViewLongitude.setText(String.format("%s", w.getLon()));
        textViewFeelsLike.setText(String.format("%s°C", w.getTempFeelsLike()));
        textViewPressure.setText(String.format("%s гПа", w.getPressure()));
        textViewHumidity.setText(String.format("%s %%", w.getHumidity()));
        textViewDirectionOfWind.setText(String.format("%s", w.getStringDirectionOfWind()));
        textViewSpeedOfWind.setText(String.format("%s м/с", w.getSpeedOfWind()));
    }

    private Weather getWeatherOfCurrentDay(double lat, double lon) {
        Weather weather = null;
        JSONObject jsonObject = NetworkUtils.getWeatherJSON(lat, lon);
        weather = JSONUtils.getWeatherFromJSON(jsonObject);
        return weather;
    }

    private Weather getWeatherOfCurrentDay(String nameOfCity) {
        Weather weather = null;
        JSONObject jsonObject = NetworkUtils.getWeatherJSON(nameOfCity);
        weather = JSONUtils.getWeatherFromJSON(jsonObject);
        return weather;
    }

    private ArrayList<Weather> getArrayOfWeather(double lat, double lon) {
        ArrayList<Weather> days = null;
        JSONObject jsonObjectOneCall = NetworkUtils.getOneCallJSON(lat, lon);
        days = JSONUtils.getArrayOfWeatherFromJSON(jsonObjectOneCall);
        return days;
    }

    private ViewGroup.LayoutParams getLayoutParamsForTempColumn(View v, double temp){
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = (int) temp * 10;
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

        textViewTempDay1.setText(String.format("%s°", (int) day1.getTemp()));
        textViewTempDay2.setText(String.format("%s°", (int) day2.getTemp()));
        textViewTempDay3.setText(String.format("%s°", (int) day3.getTemp()));
        textViewTempDay4.setText(String.format("%s°", (int) day4.getTemp()));
        textViewTempDay5.setText(String.format("%s°", (int) day5.getTemp()));
        textViewTempDay6.setText(String.format("%s°", (int) day6.getTemp()));
        textViewTempDay7.setText(String.format("%s°", (int) day7.getTemp()));

        Picasso.get().load(day1.getIconPath()).into(imageViewDay1);
        Picasso.get().load(day2.getIconPath()).into(imageViewDay2);
        Picasso.get().load(day3.getIconPath()).into(imageViewDay3);
        Picasso.get().load(day4.getIconPath()).into(imageViewDay4);
        Picasso.get().load(day5.getIconPath()).into(imageViewDay5);
        Picasso.get().load(day6.getIconPath()).into(imageViewDay6);
        Picasso.get().load(day7.getIconPath()).into(imageViewDay7);

    }

    private void setDates() {
        textViewDate1.setText(CalendarUtils.addDays(date, 1));
        textViewDate2.setText(CalendarUtils.addDays(date, 2));
        textViewDate3.setText(CalendarUtils.addDays(date, 3));
        textViewDate4.setText(CalendarUtils.addDays(date, 4));
        textViewDate5.setText(CalendarUtils.addDays(date, 5));
        textViewDate6.setText(CalendarUtils.addDays(date, 6));
        textViewDate7.setText(CalendarUtils.addDays(date, 7));

    }
}