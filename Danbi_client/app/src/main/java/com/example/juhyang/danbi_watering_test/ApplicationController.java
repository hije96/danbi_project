package com.example.juhyang.danbi_watering_test;

import android.util.Log;

import com.orm.SugarApp;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by JuHyang on 2016-02-11.
 */
public class ApplicationController extends SugarApp {
    private static ApplicationController instance;
    public static ApplicationController getInstance() { return instance; }

    //instance 객체 리턴해줌. getInstance()하면.

    @Override
    public void onCreate() {
        Log.v("알림", "oncreate -applicationcontroller");
        super.onCreate();
        ApplicationController.instance = this;
    }

    private ServerInterface api;
    //api는 서버인터페이스 객체.
    public ServerInterface getServerInterface() { return api; }
    //getServerInterface는 api를 받아준다

    private String endpoint;
    //endpoint는 baseUrl이다.

    public static final String Api_BASE_URL ="http://192.168.0.20:3000/"; //ip 입력

    public void buildServerInterface() {
        //ip와 port를 받는다.
        Log.v("알림", "buildserver 입장");

        synchronized (ApplicationController.class) {
            //synchronized ㅋㅋ십소름 이거는 이 해당 메소드가 실행되면, 실행이 끝날 때 까지 다른곳에서 실행할수가없음
            //동기화를 맞춰주는거임.


            if (api == null) { //api가 만약 null일경우. (api는 서버인터페이스다)
                //null일경우가 뭘까?api에 아직 알맹이가 들어가지않고 참조상태만 잇다는 거겟지.




                try {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Api_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    api = retrofit.create(ServerInterface.class);



                }

                catch(Exception e)
                {

                }
                //서버 연결 설정.

            }
        }
    }
}
