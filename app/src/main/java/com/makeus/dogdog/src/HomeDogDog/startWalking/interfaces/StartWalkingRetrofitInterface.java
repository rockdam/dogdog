package com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces;



import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface HomeRefreshRetrofitInterface {
@GET("/home")
@Headers("Content-Type: application/json")
Call<HomeRefreshResponse> refreshInfo();

}
