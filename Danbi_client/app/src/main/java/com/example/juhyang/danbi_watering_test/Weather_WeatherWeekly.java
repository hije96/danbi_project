package com.example.juhyang.danbi_watering_test;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_WeatherWeekly {
    String[] day;
    String[] weather;
    int[] minTemperature;
    int[] maxTemperature;

    public void setDay(String[] day) {
        String dayString[] = new String[day.length];
        for (int i = 0; i < day.length; i++) {
            String[] splitdata = day[i].split("/");
            String splitString = splitdata[0]+ "월 " + splitdata[1]+"일";
            dayString[i] =  splitString;
        }
        this.day = dayString;
    }

    public void setMaxTemperature(int[] maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMinTemperature(int[] minTemperature) {
        this.minTemperature = minTemperature;
    }

    public void setWeather(String[] weather) {
        this.weather = weather;
    }

    public int[] getMaxTemperature() {
        return maxTemperature;
    }

    public int[] getMinTemperature() {
        return minTemperature;
    }

    public String[] getDay() {
        return day;
    }

    public String[] getWeather() {
        return weather;
    }
}
