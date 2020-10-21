package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces;

import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingMonthResponse;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingdayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface DayWalkingInfoRetrofitInterface {



    @GET("/walking-day")
    @Headers("Content-Type: application/json")
    Call<WalkingdayResponse> walkingDay(@Query("date") String date, @Query("dogIdx")int dogIdx);
}
