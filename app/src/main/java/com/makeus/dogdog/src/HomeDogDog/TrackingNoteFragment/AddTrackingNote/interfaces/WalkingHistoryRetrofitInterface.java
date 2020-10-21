package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.interfaces;

import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models.DayHistory;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models.WalkinghistoryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WalkingHistoryRetrofitInterface {



    @POST("/walking-history")
    @Headers("Content-Type: application/json")
    Call<WalkinghistoryResponse> walkingDay(@Body DayHistory date);
}
