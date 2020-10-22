package com.makeus.dogdog.src.joinmember.login.interfaces;



import com.makeus.dogdog.src.joinmember.login.models.AutoLoginResponse;
import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface AutoLoginAcitivityRetrofitInterface {
@GET("/jwt")
@Headers("Content-Type: application/json")
Call<AutoLoginResponse> checkId();

}
