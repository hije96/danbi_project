package com.example.juhyang.danbi_watering_test;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by JuHyang on 2016-02-11.
 */
public interface ServerInterface {
    @GET("Water/{onoff}/{minute}")
    Call<Water_model> GetData(@Path("onoff") String onoff,
                              @Path("minute") String minute);

    @GET("Tem")
    Call<Temperature_model> GetTemp ();

    @GET("door/{onoff}")
    Call<Door_model> DoorOpen(@Path("onoff") String onoff);
}
