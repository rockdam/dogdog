package com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.interfaces;



import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models.SelectedPictureResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models.SendImagData;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface SelectedPictureRetrofitInterface {
@PATCH("/dog-img/{dogIdx}")
@Headers("Content-Type: application/json")
Call<SelectedPictureResponse> refreshInfo(@Path("dogIdx")int dogIdx, @Body SendImagData sendImagData);

}
