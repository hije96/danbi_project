package com.example.juhyang.danbi_watering_test;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_OpenWeatherAPIClient {
    final static String openWeatherURL = "http://api.openweathermap.org/data/2.5/weather";
    final static String appid = "8cbb933c3116dd59f65e17923de6240e";

    public Weather_WeatherToday getWeatherToday(double lat, double lon) {
        Weather_WeatherToday weatherWeatherToday = new Weather_WeatherToday();
        String urlString = openWeatherURL + "?lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + appid;

        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            JSONObject json = new JSONObject(getStringFromInputStream(in));

            // parse JSON
            weatherWeatherToday = parseJSONToday(json);

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;

        } catch (JSONException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }

        // set Weather_WeatherToday Object

        return weatherWeatherToday;
    }

    private Weather_WeatherToday parseJSONToday(JSONObject json) throws JSONException {
        Weather_WeatherToday w = new Weather_WeatherToday();

        w.setCity(json.getString("name"));
        w.setMainWeather(getWeathermain(json.getJSONArray("weather").getJSONObject(0).getInt("id")));
        Log.e("why", json.getJSONArray("weather").getJSONObject(0).getInt("id") + "");
        w.setTemprature(json.getJSONObject("main").getInt("temp"));
        w.setHumidity(json.getJSONObject("main").getInt("humidity"));
        w.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));
        w.setWindDeg(getDegree(json.getJSONObject("wind").getInt("deg")));

        return w;
    }

    private String getDegree(int intDegree) {
        String degree;
        if (intDegree == 0 || intDegree == 360)
            degree = "북";
        else if (intDegree == 90)
            degree = "동";
        else if (intDegree == 180)
            degree = "남";
        else if (intDegree == 270)
            degree = "서";
        else if (0 < intDegree && intDegree < 90)
            degree = "북동";
        else if (90 < intDegree && intDegree < 180)
            degree = "남동";
        else if (180 < intDegree && intDegree < 270)
            degree = "남서";
        else
            degree = "북서";

        return degree;
    }

    public Weather_WeatherWeekly getWeatherWeekly(double lat, double lon) {
        Weather_WeatherWeekly weatherWeatherWeekly = new Weather_WeatherWeekly();
        String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily" + "?lat=" + lat + "&lon=" + lon + "&units=metric&cnt=7&appid=" + appid;

        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));

            // parse JSON
            weatherWeatherWeekly = parseJSONWeekly(json);

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }
        // set Weather_WeatherToday Object

        return weatherWeatherWeekly;
    }

    private Weather_WeatherWeekly parseJSONWeekly(JSONObject json) throws JSONException {
        Weather_WeatherWeekly w = new Weather_WeatherWeekly();
        w.setWeather(getWeatherString(json.getJSONArray("list")));
        w.setDay(getDt(json.getJSONArray("list")));
        w.setMinTemperature(getMinTemperature(json.getJSONArray("list")));
        w.setMaxTemperature(getMaxTemperature(json.getJSONArray("list")));

        return w;
    }

    public Weather_WeatherThreeHour getWeatherThreeHour(double lat, double lon) {
        Weather_WeatherThreeHour weatherWeatherThreeHour = new Weather_WeatherThreeHour();
        String urlString = "http://api.openweathermap.org/data/2.5/forecast" + "?lat=" + lat + "&lon=" + lon + "&units=metric&cnt=8&appid=" + appid;
        Log.e("url", urlString);

        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));

            // parse JSON
            weatherWeatherThreeHour = parseJSONHourly(json);

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;

        } catch (JSONException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }
        // set Weather_WeatherToday Object

        return weatherWeatherThreeHour;
    }

    private Weather_WeatherThreeHour parseJSONHourly(JSONObject json) throws JSONException {
        Weather_WeatherThreeHour w = new Weather_WeatherThreeHour();
        w.setWeather(getWeatherString_hour(json.getJSONArray("list")));
        w.setTime(getDt(json.getJSONArray("list")));
        w.setTemperature(getTemperature_hour(json.getJSONArray("list")));

        return w;
    }

    public String[] getWeatherString_hour(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        String[] weatherString = new String[length];

        for (int i = 0; i < length; i++) {
            weatherString[i] = getWeathermain(jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getInt("id"));
        }
        return weatherString;
    }

    private int[] getTemperature_hour(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        int[] temp = new int[length];

        for (int i = 0; i < length; i++)
            temp[i] = jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp");

        return temp;
    }

    public String[] getWeatherString(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        String[] weatherString = new String[length];

        for (int i = 0; i < length; i++) {
            weatherString[i] = getWeathermain(jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getInt("id"));
        }
        return weatherString;
    }

    private int[] getMaxTemperature(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        int[] minTemp = new int[length];

        for (int i = 0; i < length; i++)
            minTemp[i] = jsonArray.getJSONObject(i).getJSONObject("temp").getInt("max");

        return minTemp;
    }

    private int[] getMinTemperature(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        int[] minTemp = new int[length];

        for (int i = 0; i < length; i++)
            minTemp[i] = jsonArray.getJSONObject(i).getJSONObject("temp").getInt("min");

        return minTemp;
    }

    private String[] getDt(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        String[] day = new String[length];

        for (int i = 0; i < length; i++) {
            String date = new java.text.SimpleDateFormat("MM/dd/yyyy/HH/mm/ss").format((jsonArray.getJSONObject(i).getLong("dt")) * 1000);
            day[i] = date;
        }
        return day;
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public String getWeathermain(int id) {
        String weatherMain = "미상";

        if (id == 800 || 700 < id && id < 761)
            weatherMain = "맑음";
        else if (500 <= id && id < 600)
            weatherMain = "비";
        else if (300 <= id && id < 400)
            weatherMain = "약한 비";
        else if (200 <= id && id < 300)
            weatherMain = "천둥번개";
        else if (600 <= id && id < 700)
            weatherMain = "눈";
        else if (800 < id && id < 900)
            weatherMain = "구름";
        else if (id == 903 || id == 904 || id == 905)
            weatherMain = "미상";
        else if (900 <= id && id < 900)
            weatherMain = "태풍";
        return weatherMain;
    }

}