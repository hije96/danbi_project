package com.example.juhyang.danbi_watering_test;

import com.orm.SugarRecord;

/**
 * Created by JuHyang on 2016-02-11.
 */
public class Water_AlarmData extends SugarRecord {
    int hour;
    int minute;
    int during;
    int onoff;
    String repeat;
    Boolean son;
    Boolean mon;
    Boolean tue;
    Boolean wed;
    Boolean thr;
    Boolean fri;
    Boolean sat;

    public Water_AlarmData() {}

    public Water_AlarmData(int hour, int minute, int during, int onoff, String repeat, Boolean son, Boolean mon,
                           Boolean tue, Boolean wed, Boolean thr, Boolean fri, Boolean sat) {
        this.hour = hour;
        this.minute = minute;
        this.during = during;
        this.onoff = onoff;
        this.repeat = repeat;
        this.son = son;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thr = thr;
        this.fri = fri;
        this.sat = sat;
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public int getDuring() {
        return during;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }
    public void setDuring(int during) {
        this.during = during;
    }


}