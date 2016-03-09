package com.example.juhyang.danbi_watering_test;

import android.os.AsyncTask;

/**
 * Created by user on 2016-02-26.
 */
public class Weather_OpenWeatherAPIThreeHourTask extends AsyncTask<Double, Void, Weather_WeatherThreeHour> {
    @Override
    public Weather_WeatherThreeHour doInBackground(Double... params) {
        Weather_OpenWeatherAPIClient client = new Weather_OpenWeatherAPIClient();
        double lat = params[0];
        double lon = params[1];
        // API 호출
        Weather_WeatherThreeHour w = client.getWeatherThreeHour(lat, lon);

        return w;
    }
}