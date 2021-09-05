package com.example.myweather.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myweather.MainActivity;

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

public class NetworkUtils {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static final String PARAMS_CITY = "q";
    public static final String PARAMS_API_KEY = "appid";
    public static final String PARAMS_LANG = "lang";
    public static final String PARAMS_UNITS = "units";

    public static final String VALUE_LANG = "ru";
    public static final String VALUE_UNITS = "metric";

    private static URL buildURL(String city) {
        URL url = null;
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, ApiKeyStorage.API_KEY)
                .appendQueryParameter(PARAMS_LANG, VALUE_LANG)
                .appendQueryParameter(PARAMS_UNITS, VALUE_UNITS)
                .appendQueryParameter(PARAMS_CITY, city)
                .build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static JSONObject getJSON(String city) {
        JSONObject result = null;
        URL url = buildURL(city);
        try {
            result = new DownloadJSONTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class DownloadJSONTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            if (urls == null || urls.length == 0) {
                return null;
            }
            JSONObject jsonObject = null;
            StringBuilder stringBuilder = new StringBuilder();
            URL url = null;
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                jsonObject = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
    }

}
