package com.example.juhyang.danbi_watering_test;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JuHyang on 2016-02-11.
 */
public class Water_CustomAdapter extends BaseAdapter {
    private ArrayList<Water_AlarmData> waterAlarmDatas = null;
    private LayoutInflater layoutInflater = null;
    private Context context;


    public Water_CustomAdapter(ArrayList<Water_AlarmData> waterAlarmDatas, Context ctx) {
        this.context = ctx;
        this.waterAlarmDatas = waterAlarmDatas;
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setWaterAlarmDatas(ArrayList<Water_AlarmData> waterAlarmDatas) {
        this.waterAlarmDatas = this.waterAlarmDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return waterAlarmDatas != null ? waterAlarmDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (waterAlarmDatas != null && (position >= 0 && position < waterAlarmDatas.size()) ? waterAlarmDatas.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return (waterAlarmDatas != null && (position >= 0 && position < waterAlarmDatas.size()) ? position : 0);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Water_ViewHolder waterViewHolder = new Water_ViewHolder();
        final Water_AlarmData waterAlarmData_temp = waterAlarmDatas.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);

            waterViewHolder.hour = (TextView) convertView.findViewById(R.id.textView_hour);
            waterViewHolder.hour.setText(String.valueOf(waterAlarmData_temp.hour));
            waterViewHolder.middle = (TextView) convertView.findViewById(R.id.textView_middle);
            waterViewHolder.middle.setText(" : ");
            waterViewHolder.minute = (TextView) convertView.findViewById(R.id.textView_minute);
            waterViewHolder.minute.setText(String.valueOf(waterAlarmData_temp.minute));
            waterViewHolder.during = (TextView) convertView.findViewById(R.id.textView_during);
            waterViewHolder.during.setText(String.valueOf(waterAlarmData_temp.during) + "분 동안");
            waterViewHolder.onoff = (Switch) convertView.findViewById(R.id.switch1);
            if (waterAlarmData_temp.onoff == 1) {
                waterViewHolder.onoff.setChecked(true);
            } else {
                waterViewHolder.onoff.setChecked(false);
            }
            waterViewHolder.onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    long id_long = waterAlarmData_temp.getId();
                    if (isChecked) {
                        waterAlarmData_temp.onoff = 1;
                        registAlarm(waterAlarmData_temp, id_long);
                        //알람 매니저 활동
                    } else {
                        waterAlarmData_temp.onoff = 0;
                        cancelAlarm(id_long);
                        //알람 매니저 캔슬
                    }
                    waterAlarmData_temp.save();
                }
            });
            waterViewHolder.repeat = (TextView) convertView.findViewById(R.id.textView_repeat);
            waterViewHolder.repeat.setText(waterAlarmData_temp.repeat);




        } else {
            waterViewHolder = (Water_ViewHolder) convertView.getTag();
        }
        convertView.setTag(waterViewHolder);
        return convertView;



    }

    public void cancelAlarm(long id_long)
    {
        int id = (int) id_long;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Water_AlarmReceiver.class);
        PendingIntent pending = getPendingIntent(intent, id);
        alarmManager.cancel(pending);
    }

    private PendingIntent getPendingIntent(Intent intent, long id_long)
    {
        int id = (int) id_long;
        PendingIntent pIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }

    private void registAlarm(Water_AlarmData waterAlarmData, long id_long)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Boolean[] week = {false, waterAlarmData.son, waterAlarmData.mon, waterAlarmData.tue, waterAlarmData.wed, waterAlarmData.thr, waterAlarmData.fri, waterAlarmData.sat};
        boolean isRepeat = false;
        int len = week.length;
        for (int i = 0; i < len; i++)
        {
            if (week[i])
            {
                isRepeat = true;
                break;
            }
        }

        // 알람 등록
        Intent intent = new Intent(context, Water_AlarmReceiver.class);

        long triggerTime = 0;
        if(isRepeat)
        {
            intent.putExtra("one_time", false);
            intent.putExtra("day_of_week", week);
            intent.putExtra("id",id_long);
            PendingIntent pending = getPendingIntent(intent, id_long);

            triggerTime = setTriggerTime(waterAlarmData);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, 86400000, pending);
        }
        else
        {
            intent.putExtra("one_time", true);
            intent.putExtra("id",id_long);
            PendingIntent pending = getPendingIntent(intent, id_long);

            triggerTime = setTriggerTime(waterAlarmData);
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pending);
        }

    }
    private long setTriggerTime(Water_AlarmData waterAlarmData)
    {
        // current Time
        long atime = System.currentTimeMillis();
        // timepicker
        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR_OF_DAY, waterAlarmData.hour);
        curTime.set(Calendar.MINUTE, waterAlarmData.minute);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);
        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }

}