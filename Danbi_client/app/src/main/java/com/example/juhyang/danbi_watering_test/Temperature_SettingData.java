package com.example.juhyang.danbi_watering_test;

import com.orm.SugarRecord;

/**
 * Created by JuHyang on 2016-02-12.
 */
public class Temperature_SettingData extends SugarRecord {
    int temperature;
    int humidity;

    public Temperature_SettingData() {}

    public Temperature_SettingData(int temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }



}
