package com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces;

import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StartWalkingResponse;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingBody;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface StopWalkingRetrofitInterface {

    @POST("/walking-end")
    @Headers("Content-Type: application/json")
    Call<StopWalkingResponse> stopActivity(@Body StopWalkingBody stopWalkingResponse);


}
