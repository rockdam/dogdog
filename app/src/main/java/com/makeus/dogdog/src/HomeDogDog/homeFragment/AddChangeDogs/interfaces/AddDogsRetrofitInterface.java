package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.interfaces;



import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.models.AddDogsResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface AddDogsRetrofitInterface {
@GET("/dogs")
@Headers("Content-Type: application/json")
Call<AddDogsResponse> refreshInfo();

}
