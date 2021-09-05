package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private String url = "http://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=7cf64e1c8c797fed23127143efd266f2&lang=ru&units=metric";
    public static final String API_OPEN_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&lang=ru&units=metric";
    private TextView textViewTest;
    private EditText editTextNameOfCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTest = findViewById(R.id.textViewTest);
        editTextNameOfCity = findViewById(R.id.editTextNameOfCity);
    }

    public void onClickFind(View view) {
        String nameOfCity = editTextNameOfCity.getText().toString();
        if (!nameOfCity.isEmpty()) {
            DownloadJSONTask downloadJson = new DownloadJSONTask();
            String query = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&lang=ru&units=metric", nameOfCity, "7cf64e1c8c797fed23127143efd266f2");
            downloadJson.execute(query);
        } else {
            Toast.makeText(this, "Введите название города", Toast.LENGTH_SHORT).show();
        }
    }

    public class DownloadJSONTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            URL url = null;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String city = jsonObject.getString("name");
                String temp = jsonObject.getJSONObject("main").getString("temp");
                String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                textViewTest.setText(city + "\n" + temp + "\n" + description);
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Введите верное название города", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }
}