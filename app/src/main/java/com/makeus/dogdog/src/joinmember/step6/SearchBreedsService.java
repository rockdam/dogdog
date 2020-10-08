package com.makeus.dogdog.src.joinmember.step6;

import com.makeus.dogdog.src.joinmember.step2.interfaces.ShowToastStep2;
import com.makeus.dogdog.src.joinmember.step2.interfaces.Step2RetrofitInterface;
import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;
import com.makeus.dogdog.src.joinmember.step6.interfaces.PassValueDialog;
import com.makeus.dogdog.src.joinmember.step6.interfaces.SearchBreedsListRetrofitInterface;
import com.makeus.dogdog.src.joinmember.step6.models.SearchBreedsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class SearchBreedsService {


    PassValueDialog mPassValueDialog;
    String mUserId;

    SearchBreedsResponse searchBreedsResponse;



    public SearchBreedsService(PassValueDialog passValueDialog) {
        this.mPassValueDialog = passValueDialog;

    }


    public void searchBreeds() {

        final SearchBreedsListRetrofitInterface searchBreedsListRetrofitInterface = getRetrofit().create(SearchBreedsListRetrofitInterface.class);


        searchBreedsListRetrofitInterface.getSearch().enqueue(new Callback<SearchBreedsResponse>() {


            @Override
            public void onResponse(Call<SearchBreedsResponse> call, Response<SearchBreedsResponse> response) {

                searchBreedsResponse=response.body();
                if(response.code() ==200)
                {

                    mPassValueDialog.passValueListView(searchBreedsResponse);


                }else{


//                    여기서 실패
                }
            }

            @Override
            public void onFailure(Call<SearchBreedsResponse> call, Throwable t) {

            }


        });


    }

}
