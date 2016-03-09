package com.example.juhyang.danbi_watering_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_DailyThreeHourWeather extends Fragment {
    TextView label_3hour, label_6hour, label_9hour, label_12hour, label_15hour;
    ImageView image_3hour, image_6hour, image_9hour, image_12hour, image_15hour;
    TextView temp_3hour, temp_6hour, temp_9hour, temp_12hour, temp_15hour;
    Weather_WeatherThreeHour weather_weatherThreeHour;
    ArrayList<Weather_SettingData> weather_SettingDatas;
    double args_latitude, args_longitude;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dailythreehourweather, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        getWeather();
    }

    private void getWeather() {
        Weather_OpenWeatherAPIThreeHourTask task_hourly = new Weather_OpenWeatherAPIThreeHourTask();
        // 날씨를 읽어오는 API 호출

        weather_SettingDatas = (ArrayList<Weather_SettingData>) Weather_SettingData.listAll(Weather_SettingData.class);

        if (weather_SettingDatas.size() == 0) {

        } else {
            args_latitude= weather_SettingDatas.get(0).latitude;
            args_longitude = weather_SettingDatas.get(0).longitude;

            try {
                weather_weatherThreeHour = task_hourly.execute(args_latitude, args_longitude).get();
                aboutView();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        label_3hour = (TextView) getView().findViewById(R.id.later_3hour);
        image_3hour = (ImageView) getView().findViewById(R.id.image_later_3hour);
        temp_3hour = (TextView) getView().findViewById(R.id.temperature_later_3hour);
        label_6hour = (TextView) getView().findViewById(R.id.later_6hour);
        image_6hour = (ImageView) getView().findViewById(R.id.image_later_6hour);
        temp_6hour = (TextView) getView().findViewById(R.id.temperature_later_6hour);
        label_9hour = (TextView) getView().findViewById(R.id.later_9hour);
        image_9hour = (ImageView) getView().findViewById(R.id.image_later_9hour);
        temp_9hour = (TextView) getView().findViewById(R.id.temperature_later_9hour);
        label_12hour = (TextView) getView().findViewById(R.id.later_12hour);
        image_12hour = (ImageView) getView().findViewById(R.id.image_later_12hour);
        temp_12hour = (TextView) getView().findViewById(R.id.temperature_later_12hour);
        label_15hour = (TextView) getView().findViewById(R.id.later_15hour);
        image_15hour = (ImageView) getView().findViewById(R.id.image_later_15hour);
        temp_15hour = (TextView) getView().findViewById(R.id.temperature_later_15hour);
    }

    public void aboutView() {
        TextView[] weatherLabels = {label_3hour, label_6hour, label_9hour, label_12hour, label_15hour};
        ImageView[] weatherImageViews = {image_3hour, image_6hour, image_9hour, image_12hour, image_15hour};
        TextView[] weatherTemperatures = {temp_3hour, temp_6hour, temp_9hour, temp_12hour, temp_15hour};

        int[] temperature = weather_weatherThreeHour.getTemperature();
        String[] time = weather_weatherThreeHour.getTime();
        String[] weatherString = weather_weatherThreeHour.getWeather();

        Log.e("url", weatherString[0]);

        for (int i = 0; i < 5; i++) {
            weatherLabels[i].setText(time[i] + "시");
            weatherTemperatures[i].setText(temperature[i] + "℃");
            if (weatherString[i].equals("맑음"))
                weatherImageViews[i].setImageResource(R.drawable.clear);
            else if (weatherString[i].equals("구름"))
                weatherImageViews[i].setImageResource(R.drawable.cloud);
            else if (weatherString[i].equals("비"))
                weatherImageViews[i].setImageResource(R.drawable.rain);
            else if (weatherString[i].equals("눈"))
                weatherImageViews[i].setImageResource(R.drawable.snow);
            else
                weatherImageViews[i].setImageResource(R.mipmap.ic_launcher);

        }
    }
}

