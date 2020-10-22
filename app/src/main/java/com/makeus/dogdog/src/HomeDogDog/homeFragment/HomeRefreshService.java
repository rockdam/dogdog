package com.makeus.dogdog.src.HomeDogDog.homeFragment;

import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class HomeRefreshService {


    MoveHomeAcitivity mMoveHomeAcitivity;
    HomeRefreshView homeRefreshView;
    HomeRefreshResponse homeRefreshResponse;
    public HomeRefreshService(HomeRefreshView homeRefreshView) {
        this.homeRefreshView = homeRefreshView;
    }


    public void refreshHomeView() {

        final HomeRefreshRetrofitInterface homeRefreshRetrofitInterface = getRetrofit().create(HomeRefreshRetrofitInterface.class);


        homeRefreshRetrofitInterface.refreshInfo().enqueue(new Callback<HomeRefreshResponse>() {


            @Override
            public void onResponse(Call<HomeRefreshResponse> call, Response<HomeRefreshResponse> response) {

                homeRefreshResponse=response.body();
                if(response.code() ==200)
                {

              homeRefreshView.refresh(homeRefreshResponse.getResult());


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<HomeRefreshResponse> call, Throwable t) {

            }

        });


    }

}
