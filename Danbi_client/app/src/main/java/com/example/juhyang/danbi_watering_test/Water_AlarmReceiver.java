package com.example.juhyang.danbi_watering_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JuHyang on 2016-02-11.
 */
public class Water_AlarmReceiver extends BroadcastReceiver {
    ServerInterface api = null;
    @Override
    public void onReceive(final Context context, Intent intent) {
        //ServerInterface 객체 생성
        Bundle extra = intent.getExtras();
        Log.v("확인", String.valueOf(extra.getBoolean("one_time")));
        if (extra != null) {
            boolean isOneTime = extra.getBoolean("one_time");
            if (isOneTime) {
                Log.v("태그", "반복아니유");
                long id_long = extra.getLong("id");
                int id = (int) id_long;

                Water_AlarmData waterAlarmData_temp = Water_AlarmData.findById(Water_AlarmData.class, id_long);
                waterAlarmData_temp.onoff = 0;
                watering(waterAlarmData_temp, context);
                waterAlarmData_temp.save();
            }

            else {
                Log.v("태그","반복이유");
                boolean[] week = extra.getBooleanArray("day_of_week");
                Calendar cal = Calendar.getInstance();
                long id_long = extra.getLong("id");
                int id = (int) id_long;
                Water_AlarmData waterAlarmData_temp = Water_AlarmData.findById(Water_AlarmData.class, id_long);
                watering(waterAlarmData_temp, context);
                if (!week[cal.get(Calendar.DAY_OF_WEEK)])
                    return;

                // 알람 울리기.
            }
        }
    }

    public void watering(final Water_AlarmData alramData, final Context context) {
        ApplicationController application = ApplicationController.getInstance(); //application 객체 생성
        application.buildServerInterface();//서버 생성
        Log.v("안내", "build server 성공.");

        final CountDownTimer[] timer = new CountDownTimer[1];
        api = application.getServerInterface();
        String onoff = "1";
        Call<Water_model> call = api.GetData(onoff, String.valueOf(alramData.during)); //onoff 와 분에 관한 정보를 http로 송신
        call.enqueue(new Callback<Water_model>() { //비동기방식 통신
            @Override
            public void onResponse(Response<Water_model> response, Retrofit retrofit) {
                if (response.body().result.equals("1")) {
                    Toast.makeText(context, "물 나와열.", Toast.LENGTH_LONG).show();

                    timer[0] = new CountDownTimer(alramData.during * 60 * 1000, 1000) {
                        /*1000 은 1초와 같음 ////  min 값이 선택됨에 따라 timer 초기값이 달라짐*/
                        @Override
                        public void onTick(long millisUntilFinished) { //타이머가 1초흘러갈때마다 진행 할 코드

                        }

                        @Override
                        public void onFinish() { //타이머가 종료되었을때 진행 할 코드
                            Call<Water_model> call = api.GetData("0", "0");

                            call.enqueue(new Callback<Water_model>() {
                                @Override
                                public void onResponse(Response<Water_model> response, Retrofit retrofit) {
                                    if (response.body().result.equals("1")) { //통신 성공했을경우
                                        Toast.makeText(context, "물 멈춰열.", Toast.LENGTH_LONG).show();
                                        timer[0].cancel(); //타이머 정지
                                    } else { //통신 실패 했을경우
                                        Toast.makeText(context, "어딘가 문제가 있어열.", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    Toast.makeText(context, "어딘가 문제가 있어열.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    };
                    timer[0].start(); //타이머 시작

                } else {
                    Toast.makeText(context, "어딘가 문제가 있어열.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "어딘가 문제가 있어열.", Toast.LENGTH_LONG).show();
            }
        });
    }

}

