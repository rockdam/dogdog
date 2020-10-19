package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces;

import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingMonthResponse;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingMonthResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TrackingNoteRetrofitInterface {

    @GET("/walking-month")
    @Headers("Content-Type: application/json")
    Call<WalkingMonthResponse> walkingMonth(@Query("date") String date,@Query("dogIdx")int dogIdx);
}
