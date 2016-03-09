package com.example.juhyang.danbi_watering_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialize();
    }

    private void initialize()
    {
        Handler handler =    new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();    // 액티비티 종료
            }
        };

        handler.sendEmptyMessageDelayed(0, 3000);    // ms, 3초후 종료시킴
    }
}
