package com.makeus.dogdog.src.HomeDogDog.RankingFragment.interfaces;

import com.makeus.dogdog.src.HomeDogDog.RankingFragment.models.RankingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RankingRetrofitInterface {
    @GET("/rank")
    @Headers("Content-Type: application/json")
Call<RankingResponse> refreshRank(@Query("dogIdx") int dogIdx);
}
