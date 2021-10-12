package com.example.myweather.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String ENDPOINT_WEATHER = "weather";
    public static final String ENDPOINT_ONE_CALL = "onecall";

    public static final String BASE_URL_IMAGE = "http://openweathermap.org/img/w/";
    public static final String URL_PNG = ".png";

    public static final String PARAMS_CITY = "q";
    public static final String PARAMS_LAT = "lat";
    public static final String PARAMS_LON = "lon";
    public static final String PARAMS_API_KEY = "appid";
    public static final String PARAMS_LANG = "lang";
    public static final String PARAMS_UNITS = "units";
    public static final String PARAMS_EXCLUDE = "exclude";

    public static final String VALUE_UNITS_METRIC = "metric";
    public static final String VALUE_UNITS_STANDARD = "standard";
    public static final String EXCLUDED_DATA = "current,hourly,minutely";

    public static String buildIconPath(String icon) {
        return BASE_URL_IMAGE + icon + URL_PNG;
    }
}
