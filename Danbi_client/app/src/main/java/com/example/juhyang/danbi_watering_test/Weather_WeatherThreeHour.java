package com.example.juhyang.danbi_watering_test;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_WeatherThreeHour {
    String[] time;
    String[] weather;
    int[] temperature;

    public void setTime(String[] time) {
        String[] timeData = new String[time.length];
        for (int i = 0; i < time.length; i++) {
            String[] splitdata = time[i].split("/");
            timeData[i] = splitdata[3];
        }
        this.time = timeData;
    }

    public void setTemperature(int[] minTemperature) {
        this.temperature = minTemperature;
    }

    public void setWeather(String[] weather) {
        this.weather = weather;
    }

    public int[] getTemperature() {
        return temperature;
    }

    public String[] getTime() {
        return time;
    }

    public String[] getWeather() {
        return weather;
    }
}
