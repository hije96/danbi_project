package com.example.juhyang.danbi_watering_test;

import android.os.AsyncTask;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_OpenWeatherAPIWeeklyTask extends AsyncTask<Double, Void, Weather_WeatherWeekly> {
    @Override
    public Weather_WeatherWeekly doInBackground(Double... params) {
        Weather_OpenWeatherAPIClient client = new Weather_OpenWeatherAPIClient();
        double lat = params[0];
        double lon = params[1];
        // API 호출
        Weather_WeatherWeekly w = client.getWeatherWeekly(lat, lon);

        return w;
    }
}