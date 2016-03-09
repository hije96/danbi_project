package com.camera.simplemjpeg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.juhyang.danbi_watering_test.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.URI;

public class MjpegActivity extends Activity {

    private static final boolean DEBUG = false;
    private static final String TAG = "MJPEG";

    private MjpegView mv = null;
    String URL;

    // for settings (network and resolution)
    private static final int REQUEST_SETTINGS = 0;

    private int width = 320;
    private int height = 240;

    private int ip_ad1 = 192;
    private int ip_ad2 = 168;
    private int ip_ad3 = 0;
    private int ip_ad4 = 20;
    private int ip_port = 8080;
    private String ip_command = "?action=stream";

    //기본적인 ip설정 셋팅과 URL 끝부분에 세부주소까지 설정을 해놓은 상태다.
    //?action=stream은 mjpeg 방식에서 stream 서버의 세부주소이다.

    private boolean suspending = false;

    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mjpeg);

        SharedPreferences preferences = getSharedPreferences("SAVED_VALUES", MODE_PRIVATE);
        /*
        * 소스코드를 분석하며, 진행하도록 하겠다.
        * <SharedPreferences 라이브러리는 무엇인가?>
        * 안드로이드에서 데이터 관리를 할 수 있는 방법에는 여러가지가 있다.
          SQLite DB를 이용한 데이터 관리, 서버를 거친 DB에 의한 관리, Input,OutputStreamReader를 이용한 관리,
          SharedPreferences를 이용한 관리 등이 있으며, 여기서는 SharedPreferences를 이용한 방법에 대해
          알아보겠다.
           SharedPreferences는 키와 벨류로 이루어진 파일 형식으로 데이터를 저장하는 방법으로,
           Boolean, Float, Int, Long, String 형태로 데이터를 저장할 수 있다.
           어플의 설정값, 사용자 설정 정보등을 저장하는데 적합하며 사용법은 다음과 같다.

           위에 데이터 관리를 위해서 키값 "SAVED_VALUES'에다가 MODE_PRIVATE라는 값을 저장한다.

        *
        * */

        //데이터 호출! "키값", "VALUE"로 해서 값을 불러와서 저장한다.

        width = preferences.getInt("width", width);
        height = preferences.getInt("height", height);
        ip_ad1 = preferences.getInt("ip_ad1", ip_ad1);
        ip_ad2 = preferences.getInt("ip_ad2", ip_ad2);
        ip_ad3 = preferences.getInt("ip_ad3", ip_ad3);
        ip_ad4 = preferences.getInt("ip_ad4", ip_ad4);
        ip_port = preferences.getInt("ip_port", ip_port);
        ip_command = preferences.getString("ip_command", ip_command);


        /*
        * <StringBuilder 는 뭘까?>
        *
        * StringBuilder는 String과 마찬가지로 문자열을 남는 역할을 하지만, 차이점이 있다.
        *그것은 문자열을 "수정할 수 "있다는 것이다.
        *
        *      StringBuilder sb = new StringBuilder();
        *
        * 로 선언을 한다.
        *
        *
        * */
        StringBuilder sb = new StringBuilder();


        /*
        StringBuilder 객체가 보유할 문자열의 내용을 기입합니다. 방법은 다음과 같습니다.
        1. 따옴표(") + 문자열 내용 + 따옴표(")
            (예) StringBuidler greeting = new StringBuilder("Hello.");
         */

        String s_http = "http://";
        String s_dot = ".";
        String s_colon = ":";
        String s_slash = "/";
        sb.append(s_http);
        //append는 추가하는 함수이다. sb라는 문자열 배열에 s_http를 추가하는것이다.
        //이 아래도 모두 추가추가 하는것인데, 그렇게 추가되면 우리가 원하는 주소체계가 완성된다.
        sb.append(ip_ad1);
        sb.append(s_dot);
        sb.append(ip_ad2);
        sb.append(s_dot);
        sb.append(ip_ad3);
        sb.append(s_dot);
        sb.append(ip_ad4);
        sb.append(s_colon);
        sb.append(ip_port);
        sb.append(s_slash);
        sb.append(ip_command);

        URL = new String(sb);
        //이렇게 생성된 sb 스트링을 URL String에 저장한다.URL완성이다!


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //위에 소스는 스테이터스바와 타이틀바를 없애는 것이다.
        //스테이터스바는 맨위에 베터리나 이런 표시되는걸 말하는 곳이고,
        //타이틀바는 어플자체에 있는 타이틀바를 말하는 것이다.


        //기본적으로 URL을 만들어놓고 그다음에, 화면을 띄운다.
        //위에서 먼저 URL String을 만들어 놨기때문에,

        mv = (MjpegView) findViewById(R.id.mv);



        if (mv != null) {
            mv.setResolution(width, height);
        }
        //mv가 널이 아니라면, 해상도를 결정하는 것.려


        setTitle(R.string.title_connecting);
        //타이틀을 설정한다. 이 타이틀 설정은, values의 string에서 확인이 가능하다.
        //연결됬다는 표시를 나타낸다.

        new DoRead().execute(URL);
        //URL로부터 정보를 읽어들인다.

    }

    public void onResume() {
        //onResume 함수는 포커스를 다시 얻었을 때 불린다.


        if (DEBUG) Log.d(TAG, "onResume()");
        super.onResume();
        if (mv != null) {
            if (suspending) {
                new DoRead().execute(URL);
                suspending = false;
            }
        }

    }


    public void onStart() {

        //onStart() 함수는 Activity 다시 화면이 다시 돌아 올 때 불린다.


        if (DEBUG) Log.d(TAG, "onStart()");

        super.onStart();
    }

    public void onPause() {
        //onPause() 함수는 Activity 위에 다른 Activity 가 올라오거나 하여 focus 를 잃었을 때 불린다.
        //예를 들어 폰의 alarm 이 울리거나 전화가 오는 경우 onPause() 함수가 불린다

        if (DEBUG) Log.d(TAG, "onPause()");

        super.onPause();

        if (mv != null) {
            if (mv.isStreaming()) {
                mv.stopPlayback();
                suspending = true;
            }
        }
    }

    public void onStop() {
        //onStop() 함수는 Activity 가 완전히 화면을 벗어날 때 불린다.
        //예를 들어 홈키를 눌러 홈화면으로 이동하거나 전화가 와서 화면을 완전히 덮는 경우 불린다.
        if (DEBUG) Log.d(TAG, "onStop()");


        super.onStop();
    }


    public void onDestroy() {
        if (DEBUG) Log.d(TAG, "onDestroy()");

        if (mv != null) {
            mv.freeCameraMemory();
        }

        super.onDestroy();
    }
    public void setImageError() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                setTitle(R.string.title_imageerror);
                return;
            }
        });
    }

    public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
        protected MjpegInputStream doInBackground(String... url) {
            //TODO: if camera has authentication deal with it and don't just not work
            HttpResponse res = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpParams httpParams = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 5 * 1000);
            HttpConnectionParams.setSoTimeout(httpParams, 5 * 1000);

            if (DEBUG) Log.d(TAG, "1. Sending http request");

            try {
                res = httpclient.execute(new HttpGet(URI.create(url[0])));
                if (DEBUG)
                    Log.d(TAG, "2. Request finished, status = " + res.getStatusLine().getStatusCode());
                if (res.getStatusLine().getStatusCode() == 401) {
                    //You must turn off camera User Access Control before this will work
                    return null;
                }

                return new MjpegInputStream(res.getEntity().getContent());
            }
            catch (ClientProtocolException e) {
                if (DEBUG) {
                    e.printStackTrace();
                    Log.d(TAG, "Request failed-ClientProtocolException", e);
                }
                //Error connecting to camera
            } catch (IOException e) {
                if (DEBUG) {
                    e.printStackTrace();
                    Log.d(TAG, "Request failed-IOException", e);
                }
                //Error connecting to camera
            }
            return null;
        }

        protected void onPostExecute(MjpegInputStream result) {
            mv.setSource(result);
            if (result != null) {
                result.setSkip(1);
                setTitle(R.string.app_name);
            } else {
                setTitle(R.string.title_disconnected);
            }
            mv.setDisplayMode(MjpegView.SIZE_FULLSCREEN);

            //디스플레이 모드를 FULLSCREEN으로하면, 풀스크린으로 나온다.

            mv.showFps(false);
        }
    }

    public class RestartApp extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... v) {
            MjpegActivity.this.finish();
            return null;
        }

        protected void onPostExecute(Void v) {
            startActivity((new Intent(MjpegActivity.this, MjpegActivity.class)));
        }
    }
}
