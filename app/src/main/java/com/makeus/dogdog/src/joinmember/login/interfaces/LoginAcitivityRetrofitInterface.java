package com.makeus.dogdog.src.joinmember.login.interfaces;



import com.makeus.dogdog.src.joinmember.login.models.AutoLoginResponse;
import com.makeus.dogdog.src.joinmember.login.models.LogInResponse;
import com.makeus.dogdog.src.joinmember.login.models.LoginBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginAcitivityRetrofitInterface {
@POST("/sign-in")
@Headers("Content-Type: application/json")
Call<LogInResponse> checkBody(@Body LoginBody loginBody);



}
