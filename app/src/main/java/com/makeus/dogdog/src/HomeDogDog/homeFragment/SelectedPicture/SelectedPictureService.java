package com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture;

import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.interfaces.SelectedPictureRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.interfaces.SelectedPictureView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models.SelectedPictureResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models.SendImagData;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;
import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;

public class SelectedPictureService {


    SelectedPictureView selectedPictureView;
    SelectedPictureResponse selectedPictureResponse;
    SendImagData sendImagData;
    public SelectedPictureService(SelectedPictureView selectedPictureView, SendImagData sendImagData) {
        this.selectedPictureView = selectedPictureView;
        this.sendImagData=sendImagData;
    }


    public void refreshHomeView() {

        final SelectedPictureRetrofitInterface selectedPictureRetrofitInterface = getRetrofit().create(SelectedPictureRetrofitInterface.class);


        selectedPictureRetrofitInterface.refreshInfo(sSharedPreferences.getInt("dogIdx",1),sendImagData).enqueue(new Callback<SelectedPictureResponse>() {
    //여기 위험 가능성 높음

            @Override
            public void onResponse(Call<SelectedPictureResponse> call, Response<SelectedPictureResponse> response) {

                selectedPictureResponse=response.body();
                if(response.code() ==200)
                {

                    selectedPictureView.refresh(selectedPictureResponse);


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<SelectedPictureResponse> call, Throwable t) {

            }

        });


    }

}
