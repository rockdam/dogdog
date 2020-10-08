package com.makeus.dogdog.src.joinmember.step2.interfaces;



import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface Step2RetrofitInterface {
@GET("/duplicated-email/{email}")
@Headers("Content-Type: application/json")
Call<DuplicateUserIdResponse> getUserId(@Path("email") String userId);

}
