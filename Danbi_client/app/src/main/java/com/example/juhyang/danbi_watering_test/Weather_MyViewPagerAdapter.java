package com.example.juhyang.danbi_watering_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_MyViewPagerAdapter extends FragmentStatePagerAdapter {

	/*
     * 이 클래스의 부모생성자 호출시 인수로 반드시 FragmentManager객체를 넘겨야한다.
	 * 이 객체는 Activity에서만 만들수 있고, 여기서사용중인 Fragment가 v4이기 때문에
	 * Activity중에서도 ActionBarActivity에서 얻어와야한다.
	 */

    Fragment[] fragments = new Fragment[2];


    public Weather_MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = new Weather_DailyThreeHourWeather();
        fragments[1] = new Weather_WeeklyHourWeather();
    }

    //아래의 메서드들의 호출 주체는 ViewPager이다.
    //ListView와 원리가 같다.
    // 여러 프레그먼트 중 어떤 프레그먼트를 보여줄지 결정
    public Fragment getItem(int arg0) {
        return fragments[arg0];
    }

    // 보여질 프레그먼트가 몇개인지 결정
    public int getCount() {
        return fragments.length;
    }

}