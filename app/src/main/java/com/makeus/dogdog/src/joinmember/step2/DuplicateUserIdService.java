package com.makeus.dogdog.src.joinmember.step2;

import android.widget.Toast;

import com.makeus.dogdog.src.joinmember.step2.interfaces.ShowToastStep2;
import com.makeus.dogdog.src.joinmember.step2.interfaces.Step2RetrofitInterface;
import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuplicateUserIdService {


    ShowToastStep2 mShowToastStep2;
    String mUserId;

    DuplicateUserIdResponse duplicateUserIdResponse;
    public DuplicateUserIdService(ShowToastStep2 ShowToastStep2, String userId) {
        this.mShowToastStep2 = ShowToastStep2;
        this.mUserId = userId;
    }


    public void checkDuplicatedId() {

        final Step2RetrofitInterface step2RetrofitInterface = getRetrofit().create(Step2RetrofitInterface.class);


        step2RetrofitInterface.getUserId(mUserId).enqueue(new Callback<DuplicateUserIdResponse>() {


            @Override
            public void onResponse(Call<DuplicateUserIdResponse> call, Response<DuplicateUserIdResponse> response) {

                duplicateUserIdResponse=response.body();
                if(response.code() ==200)
                {

                    mShowToastStep2.checkDuplicatId(duplicateUserIdResponse);


                }else{


                    mShowToastStep2.checkDuplicatId(duplicateUserIdResponse);
                }
            }

            @Override
            public void onFailure(Call<DuplicateUserIdResponse> call, Throwable t) {

                System.out.println("jd");

            }
        });


    }

}
