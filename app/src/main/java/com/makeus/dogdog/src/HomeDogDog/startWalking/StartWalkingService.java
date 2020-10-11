package com.makeus.dogdog.src.HomeDogDog.startWalking;

import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;
import com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces.StartWalkingRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces.StartWalkingView;
import com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces.StopWalkingRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StartWalkingResponse;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingBody;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingResponse;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class StartWalkingService {


    StartWalkingView mStartWalkingView;
    StartWalkingRetrofitInterface mStartWalkingRetrofitInterface;
    StartWalkingResponse mStartWalkingResponse;
    int mDogIdx;
    StopWalkingResponse mStopWalkingResponse;

    StopWalkingBody mStopWalkingBody;
    public StartWalkingService(StartWalkingView startWalkingView,int dogIdx) {
        this.mStartWalkingView = startWalkingView;
        this.mDogIdx=dogIdx;
    }
    public StartWalkingService(StartWalkingView startWalkingView, StopWalkingBody mStopWalkingBody) {
        this.mStartWalkingView = startWalkingView;
        this.mStopWalkingBody=mStopWalkingBody;
    }


    public void refreshStartWalkingView() {

        final StartWalkingRetrofitInterface mStartWalkingRetrofitInterface  = getRetrofit().create(StartWalkingRetrofitInterface.class);


        mStartWalkingRetrofitInterface.refreshInfo(mDogIdx).enqueue(new Callback<StartWalkingResponse>() {


            @Override
            public void onResponse(Call<StartWalkingResponse> call, Response<StartWalkingResponse> response) {

                mStartWalkingResponse=response.body();
                if(response.code() ==200)
                {

                    mStartWalkingView.refresh(mStartWalkingResponse.getResult());


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<StartWalkingResponse> call, Throwable t) {

            }

        });


    }

    public void terminatedStartWalking() {

        final StopWalkingRetrofitInterface stopWalkingRetrofitInterface  = getRetrofit().create(StopWalkingRetrofitInterface.class);


        stopWalkingRetrofitInterface.stopActivity(mStopWalkingBody).enqueue(new Callback<StopWalkingResponse>() {


            @Override
            public void onResponse(Call<StopWalkingResponse> call, Response<StopWalkingResponse> response) {

                mStopWalkingResponse=response.body();
                if(response.code() ==200)
                {

                    mStartWalkingView.terminate();


                }// 자동로그인 안되면 로그인 해야지 .




            }

            @Override
            public void onFailure(Call<StopWalkingResponse> call, Throwable t) {

            }




        });


    }

}
