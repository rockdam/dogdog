package com.makeus.dogdog.src.joinmember.step6.interfaces;



import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;
import com.makeus.dogdog.src.joinmember.step6.models.SearchBreedsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface SearchBreedsListRetrofitInterface {
@GET("/breeds")
@Headers("Content-Type: application/json")
Call<SearchBreedsResponse> getSearch();

}
