package com.example.juhyang.danbi_watering_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class Weather_WeeklyHourWeather extends Fragment {
    TextView day1_label, day2_label, day3_label, day4_label, day5_label, day6_label, day7_label;
    ImageView day1_image, day2_image, day3_image, day4_image, day5_image, day6_image, day7_image;
    TextView day1_temp, day2_temp, day3_temp, day4_temp, day5_temp, day6_temp, day7_temp;
    Weather_WeatherWeekly weatherWeatherWeekly;
    ArrayList<Weather_SettingData> weather_SettingDatas;
    double args_latitude, args_longitude;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weeklyweather, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        getWeather();
    }

    private void getWeather() {
        Weather_OpenWeatherAPIWeeklyTask task_weekly = new Weather_OpenWeatherAPIWeeklyTask();
        weather_SettingDatas = (ArrayList<Weather_SettingData>) Weather_SettingData.listAll(Weather_SettingData.class);
        if (weather_SettingDatas.size() == 0) {

        } else {
            args_latitude = weather_SettingDatas.get(0).latitude;
            args_longitude = weather_SettingDatas.get(0).longitude;
            try {
                weatherWeatherWeekly = task_weekly.execute(args_latitude, args_longitude).get();
                aboutView();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void aboutView() {
        TextView[] weatherLabels = {day1_label, day2_label, day3_label, day4_label, day5_label, day6_label, day7_label};
        ImageView[] weatherImageViews = {day1_image, day2_image, day3_image, day4_image, day5_image, day6_image, day7_image};
        TextView[] weatherTemperatures = {day1_temp, day2_temp, day3_temp, day4_temp, day5_temp, day6_temp, day7_temp};

        String[] day = weatherWeatherWeekly.getDay();
        int[] min = weatherWeatherWeekly.getMinTemperature();
        int[] max = weatherWeatherWeekly.getMaxTemperature();
        String[] weatherString = weatherWeatherWeekly.getWeather();

        for (int i = 0; i < weatherString.length; i++) {
            weatherLabels[i].setText(day[i]);
            weatherTemperatures[i].setText(min[i] + " / " + max[i]);
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

    private void initView() {
        day1_label = (TextView) getView().findViewById(R.id.later_day1);
        day1_image = (ImageView) getView().findViewById(R.id.imageWeather_day1);
        day1_temp = (TextView) getView().findViewById(R.id.temperature_day1);
        day2_label = (TextView) getView().findViewById(R.id.later_day2);
        day2_image = (ImageView) getView().findViewById(R.id.imageWeather_day2);
        day2_temp = (TextView) getView().findViewById(R.id.temperature_day2);
        day3_label = (TextView) getView().findViewById(R.id.later_day3);
        day3_image = (ImageView) getView().findViewById(R.id.imageWeather_day3);
        day3_temp = (TextView) getView().findViewById(R.id.temperature_day3);
        day4_label = (TextView) getView().findViewById(R.id.later_day4);
        day4_image = (ImageView) getView().findViewById(R.id.imageWeather_day4);
        day4_temp = (TextView) getView().findViewById(R.id.temperature_day4);
        day5_label = (TextView) getView().findViewById(R.id.later_day5);
        day5_image = (ImageView) getView().findViewById(R.id.imageWeather_day5);
        day5_temp = (TextView) getView().findViewById(R.id.temperature_day5);
        day6_label = (TextView) getView().findViewById(R.id.later_day6);
        day6_image = (ImageView) getView().findViewById(R.id.imageWeather_day6);
        day6_temp = (TextView) getView().findViewById(R.id.temperature_day6);
        day7_label = (TextView) getView().findViewById(R.id.later_day7);
        day7_image = (ImageView) getView().findViewById(R.id.imageWeather_day7);
        day7_temp = (TextView) getView().findViewById(R.id.temperature_day7);
    }
}

