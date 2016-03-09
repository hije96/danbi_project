package com.example.juhyang.danbi_watering_test;

import android.os.AsyncTask;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_OpenWeatherAPIDayTask extends AsyncTask<Double, Void, Weather_WeatherToday> {
    @Override
    public Weather_WeatherToday doInBackground(Double... params) {
        Weather_OpenWeatherAPIClient client = new Weather_OpenWeatherAPIClient();
        double lat = params[0];
        double lon = params[1];
        // API 호출
        Weather_WeatherToday w = client.getWeatherToday(lat, lon);

        return w;
    }
}