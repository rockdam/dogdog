package com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces;



import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StartWalkingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface StartWalkingRetrofitInterface {
@GET("/walking-start/{dogIdx}")
@Headers("Content-Type: application/json")
Call<StartWalkingResponse> refreshInfo(@Path("dogIdx")int dogIdx);


}
