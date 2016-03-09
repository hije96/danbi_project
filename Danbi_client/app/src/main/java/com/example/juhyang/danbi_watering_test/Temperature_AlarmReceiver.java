package com.example.juhyang.danbi_watering_test;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by JuHyang on 2016-02-13.
 */
public class Temperature_AlarmReceiver extends BroadcastReceiver  {
    NotificationManager mNM;
    ServerInterface api = null;
    ArrayList<Temperature_SettingData> temperature_settingDatas;
    ArrayList<Door_modeData> door_modeData;
    Temperature_SettingData temperature_settingData_temp;
    private Notification mNoti1, mNoti2;
    @Override
    public void onReceive(final Context context, Intent intent) {
        temperature_settingDatas = (ArrayList<Temperature_SettingData>) Temperature_SettingData.listAll(Temperature_SettingData.class);
        temperature_settingData_temp = temperature_settingDatas.get(0);
        ApplicationController application = ApplicationController.getInstance(); //application 객체 생성
        application.buildServerInterface();//서버 생성
        Log.v("안내", "build server 성공.");

        api = application.getServerInterface();
        Call<Temperature_model> call = api.GetTemp();

        call.enqueue(new Callback<Temperature_model>() {
            @Override
            public void onResponse(Response<Temperature_model> response, Retrofit retrofit) {
                mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                float current_temp_float = Float.parseFloat(response.body().tem);
                float current_hum_float = Float.parseFloat(response.body().hum);
                int current_temp = (int) current_temp_float;
                int current_hum = (int) current_hum_float;
                Log.v("현재온도", String.valueOf(current_temp));
                Log.v("현재습도", String.valueOf(current_hum));
                Log.v("설정온도", String.valueOf(temperature_settingData_temp.temperature));
                Log.v("설정습도", String.valueOf(temperature_settingData_temp.humidity));
                PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0 , new Intent (context, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);
                if (temperature_settingData_temp.temperature < current_temp - 3) {
                    Log.v("안내", "if 문 들어왓음");
                    //노티피케이션
                    mNoti1 = new NotificationCompat.Builder(context)
                            .setContentTitle ("온도")
                            .setContentText("낮음낮음")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setTicker("알림 !!!")
                            .setAutoCancel(true)
                            .setContentIntent(mPendingIntent)
                            .build();

                    mNM.notify(7777, mNoti1);

                    door_modeData = (ArrayList<Door_modeData>) Door_modeData.listAll(Door_modeData.class);
                    if (door_modeData.get(0).mode) {
                        CloseDoor(context);
                    }

                } else if (temperature_settingData_temp.temperature > current_temp + 3) {
                    Log.v("안내", "if 문 들어왓음");
                    //노티피케이션
                    mNoti1 = new NotificationCompat.Builder(context)
                            .setContentTitle ("온도")
                            .setContentText("높음높음")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setTicker("알림 !!!")
                            .setAutoCancel(true)
                            .setContentIntent(mPendingIntent)
                            .build();

                    mNM.notify(7777, mNoti1);


                    door_modeData = (ArrayList<Door_modeData>) Door_modeData.listAll(Door_modeData.class);
                    if (door_modeData.get(0).mode) {
                        OpenDoor(context);
                    }
                }
                if (temperature_settingData_temp.humidity < current_hum - 3) {
                    mNoti2 = new NotificationCompat.Builder(context)
                            .setContentTitle ("습도")
                            .setContentText("낮음낮음 ")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setTicker("알림 !!!")
                            .setAutoCancel(true)
                            .setContentIntent(mPendingIntent)
                            .build();

                    mNM.notify(8888, mNoti2);

                    door_modeData = (ArrayList<Door_modeData>) Door_modeData.listAll(Door_modeData.class);
                    if (door_modeData.get(0).mode) {
                        CloseDoor(context);
                    }

                } else if (temperature_settingData_temp.humidity > current_hum + 3) {
                    mNoti2 = new NotificationCompat.Builder(context)
                            .setContentTitle ("습도")
                            .setContentText("높음높음")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setTicker("알림 !!!")
                            .setAutoCancel(true)
                            .setContentIntent(mPendingIntent)
                            .build();

                    mNM.notify(8888, mNoti2);

                    door_modeData = (ArrayList<Door_modeData>) Door_modeData.listAll(Door_modeData.class);
                    if (door_modeData.get(0).mode) {
                        OpenDoor(context);
                    }

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context,"실패",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void OpenDoor (final Context context) {
        ApplicationController application = ApplicationController.getInstance(); //application 객체 생성
        application.buildServerInterface();//서버 생성
        Log.v("안내", "build server 성공.");

        api = application.getServerInterface();

        Call<Door_model> call = api.DoorOpen ("1");

        call.enqueue(new Callback<Door_model>() {
            @Override
            public void onResponse(Response<Door_model> response, Retrofit retrofit) {

                if (response.body().result.equals("1")) {
                    Toast.makeText(context, "문이 열립니다.",
                            Toast.LENGTH_LONG).show();
                } else if (response.body().result.equals("0")) {
                    Toast.makeText(context, "문이 이미 열려있습니다.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "어딘가 문제가 있어열.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void CloseDoor (final Context context) {
        //닫힘
        ApplicationController application = ApplicationController.getInstance(); //application 객체 생성
        application.buildServerInterface();//서버 생성
        Log.v("안내", "build server 성공.");

        api = application.getServerInterface();

        Call<Door_model> call = api.DoorOpen("0");

        call.enqueue(new Callback<Door_model>() {
            @Override
            public void onResponse(Response<Door_model> response, Retrofit retrofit) {
                if (response.body().result.equals("1")) {
                    Toast.makeText(context, "문이 닫힙니다.",
                            Toast.LENGTH_LONG).show();
                } else if (response.body().result.equals("0")) {
                    Toast.makeText(context, "문이 이미 닫혀있습니다",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "어딘가 문제가 있어열.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
