package com.makeus.dogdog.src.HomeDogDog.MypageFragment.interfaces;

import com.makeus.dogdog.src.HomeDogDog.MypageFragment.models.MyPageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MyPageRetrofitInterface {
    @GET("/my-page")
    @Headers("Content-Type: application/json")
Call<MyPageResponse> refreshMypage(@Query("dogIdx") int dogIdx);
}
