package com.makeus.dogdog.src.HomeDogDog.RankingFragment;

import android.util.Log;

import com.makeus.dogdog.src.HomeDogDog.RankingFragment.interfaces.RankingRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.interfaces.RankingView;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.models.RankingResponse;
import com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces.StartWalkingRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces.StartWalkingView;
import com.makeus.dogdog.src.HomeDogDog.startWalking.interfaces.StopWalkingRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StartWalkingResponse;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingBody;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;
import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;

public class RankingService {


    RankingView mRankingView;
    RankingRetrofitInterface mRankingRetrofitInterface;
    int mDogIdx;
    RankingResponse mRankingResponse;

    StopWalkingBody mStopWalkingBody;
    public RankingService(RankingView mRankingView) {
        this.mRankingView = mRankingView;
        Log.e("왜",""+sSharedPreferences.getInt("dogIdx",1));
    }



    public void refreshRankingView() {

        final    RankingRetrofitInterface mRankingRetrofitInterface = getRetrofit().create(RankingRetrofitInterface.class);


        mRankingRetrofitInterface.refreshRank(sSharedPreferences.getInt("dogIdx",1)).enqueue(new Callback<RankingResponse>() {



            @Override
            public void onResponse(Call<RankingResponse> call, Response<RankingResponse> response) {

                mRankingResponse=response.body();
                if(response.code() ==200)
                {

                    mRankingView.refreshRanking(mRankingResponse.getResult());


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<RankingResponse> call, Throwable t) {

                System.out.println("왜"+t.getMessage());
            }

        });


    }


}
