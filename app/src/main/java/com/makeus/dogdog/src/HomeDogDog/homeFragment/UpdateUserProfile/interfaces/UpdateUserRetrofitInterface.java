package com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.interfaces;



import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.models.UserUpdateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface UpdateUserRetrofitInterface {
@GET("/edit-info")
@Headers("Content-Type: application/json")
Call<UserUpdateResponse> refreshInfo(@Query("dogIdx") int dogIdx);

}
