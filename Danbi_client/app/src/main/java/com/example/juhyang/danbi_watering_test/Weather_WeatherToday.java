package com.example.juhyang.danbi_watering_test;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_WeatherToday {
    int lat;
    int ion;
    int temprature;
    int cloudy;
    int humidity;

    double windSpeed;
    String city;
    String windDeg;
    String mainWeather;

    public void setLat(int lat) {
        this.lat = lat;
    }

    public void setIon(int ion) {
        this.ion = ion;
    }

    public void setTemprature(int t) {
        this.temprature = t;
    }

    public void setCloudy(int cloudy) {
        this.cloudy = cloudy;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }
    public void setMainWeather(String mainWeather){
        this.mainWeather = mainWeather;
    }


    public int getLat() {
        return lat;
    }

    public int getIon() {
        return ion;
    }

    public int getTemprature() {
        return temprature;
    }

    public int getCloudy() {
        return cloudy;
    }

    public String getCity() {
        return city;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getWindDeg() {
        return windDeg;
    }
    public String getMainWeather(){
        return mainWeather;
    }
}