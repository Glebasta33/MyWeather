package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweather.utils.JSONUtils;
import com.example.myweather.utils.NetworkUtils;

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
           JSONObject jsonObject = NetworkUtils.getJSON(nameOfCity);
           if (jsonObject == null) {
               Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
           } else {
               Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
           }
        } else {
            Toast.makeText(this, "Введите название города", Toast.LENGTH_SHORT).show();
        }
    }


}