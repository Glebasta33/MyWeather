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

    public static final String BASE_URL_WEATHER = "http://api.openweathermap.org/data/2.5/weather";
    public static final String BASE_URL_ONE_CALL = "https://api.openweathermap.org/data/2.5/onecall";
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
    public static final String VALUE_MINUTELY = "minutely";
    public static final String VALUE_HOURLY = "hourly";

    private static URL buildWeatherURL(String city, String lang, String units) {
        URL url = null;
        Uri uri = Uri.parse(BASE_URL_WEATHER).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, ApiKeyStorage.API_KEY)
                .appendQueryParameter(PARAMS_LANG, lang)
                .appendQueryParameter(PARAMS_UNITS, units)
                .appendQueryParameter(PARAMS_CITY, city)
                .build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static URL buildWeatherURL(double lat, double lon, String lang, String units) {
        URL url = null;
        Uri uri = Uri.parse(BASE_URL_WEATHER).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, ApiKeyStorage.API_KEY)
                .appendQueryParameter(PARAMS_LANG, lang)
                .appendQueryParameter(PARAMS_UNITS, units)
                .appendQueryParameter(PARAMS_LAT, Double.toString(lat))
                .appendQueryParameter(PARAMS_LON, Double.toString(lon))
                .build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    private static URL buildOneCallURL(double lat, double lon, String lang, String units) {
        URL url = null;
        Uri uri = Uri.parse(BASE_URL_ONE_CALL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, ApiKeyStorage.API_KEY)
                .appendQueryParameter(PARAMS_LANG, lang)
                .appendQueryParameter(PARAMS_UNITS, units)
                .appendQueryParameter(PARAMS_EXCLUDE, VALUE_HOURLY)
                .appendQueryParameter(PARAMS_EXCLUDE, VALUE_MINUTELY)
                .appendQueryParameter(PARAMS_LAT, Double.toString(lat))
                .appendQueryParameter(PARAMS_LON, Double.toString(lon))
                .build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static JSONObject getWeatherJSON(String city, String lang, String units) {
        JSONObject result = null;
        URL url = buildWeatherURL(city, lang, units);
        try {
            result = new DownloadJSONTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static JSONObject getOneCallJSON(double lat, double lon, String lang, String units) {
        JSONObject result = null;
        URL url = buildOneCallURL(lat, lon, lang, units);
        try {
            result = new DownloadJSONTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static JSONObject getWeatherJSON(double lat, double lon, String lang, String units) {
        JSONObject result = null;
        URL url = buildWeatherURL(lat, lon, lang, units);
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

    // deprecated
    public static class DownloadIconTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            URL url = null;
            String urlArgument = BASE_URL_IMAGE + strings[0] + URL_PNG;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(urlArgument);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    // deprecated
    public static Bitmap getIcon(Weather weather) {
        Bitmap bitmap = null;
        try {
            bitmap = new DownloadIconTask().execute(weather.getIcon()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Weather getWeatherOfCurrentDay(double lat, double lon, String lang, String units) {
        Weather weather = null;
        JSONObject jsonObject = getWeatherJSON(lat, lon, lang, units);
        weather = JSONUtils.getWeatherFromJSON(jsonObject);
        return weather;
    }

    public static Weather getWeatherOfCurrentDay(String nameOfCity, String lang, String units) {
        Weather weather = null;
        JSONObject jsonObject = getWeatherJSON(nameOfCity, lang, units);
        weather = JSONUtils.getWeatherFromJSON(jsonObject);
        return weather;
    }

    public static ArrayList<Weather> getArrayOfWeather(double lat, double lon, String lang, String units) {
        ArrayList<Weather> days = null;
        JSONObject jsonObjectOneCall = getOneCallJSON(lat, lon, lang, units);
        days = JSONUtils.getArrayOfWeatherFromJSON(jsonObjectOneCall);
        return days;
    }

}
