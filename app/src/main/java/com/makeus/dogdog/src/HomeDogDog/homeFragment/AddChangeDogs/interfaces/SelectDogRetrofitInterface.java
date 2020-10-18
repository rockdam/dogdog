package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.interfaces;

import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.models.AddDogsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface SelectDogRetrofitInterface {

    @PATCH("/displayed-dog/{dogIdx}")
    @Headers("Content-Type: application/json")
    Call<AddDogsResponse> refreshInfo(@Path("dogIdx")int dogIdx);
}
