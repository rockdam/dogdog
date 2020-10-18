package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.interfaces;



import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.models.AddDogInfo;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.models.DogsUpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddDogsRetrofitInterface {
    @POST("/dog")
    @Headers("Content-Type: application/json")
    Call<DogsUpdateResponse> refreshInfo(@Body AddDogInfo addDogInfo);

}
