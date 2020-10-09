package com.makeus.dogdog.src.joinmember.step6.interfaces;

import com.makeus.dogdog.src.joinmember.step6.models.JoinMemberResponse;
import com.makeus.dogdog.src.joinmember.step6.models.PostJoinMember;
import com.makeus.dogdog.src.joinmember.step6.models.SearchBreedsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JoinMemberRetrofitInterface {

    @POST("/sign-up")
    @Headers("Content-Type: application/json")
    Call<JoinMemberResponse> postJoinMember(@Body PostJoinMember postJoinMember);

}
