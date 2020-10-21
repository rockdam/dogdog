package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces;

import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models.DayHistory;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models.WalkinghistoryResponse;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.GetWalkinghistoryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetWalkingHistoryRetrofitInterface {



    @GET("/walking-history")
    @Headers("Content-Type: application/json")
    Call<GetWalkinghistoryResponse> walkingDay(@Query("date")String date, @Query("dogIdx")int dogIdx);
}
