package com.makeus.dogdog.src.HomeDogDog.MypageFragment;

import android.util.Log;

import com.makeus.dogdog.src.HomeDogDog.MypageFragment.interfaces.MyPageRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.MypageFragment.interfaces.MypageView;
import com.makeus.dogdog.src.HomeDogDog.MypageFragment.models.MyPageResponse;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.interfaces.RankingRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.interfaces.RankingView;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.models.RankingResponse;
import com.makeus.dogdog.src.HomeDogDog.startWalking.models.StopWalkingBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;
import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;

public class MyPageService {


    MypageView mypageView;

    int mDogIdx;
    MyPageResponse mRankingResponse;

    public MyPageService(    MypageView mypageView) {
        this.mypageView = mypageView;
        Log.e("왜",""+sSharedPreferences.getInt("dogIdx",1));
    }



    public void refreshMypage() {

        final    MyPageRetrofitInterface myPageRetrofitInterface = getRetrofit().create(MyPageRetrofitInterface.class);


        myPageRetrofitInterface.refreshMypage(sSharedPreferences.getInt("dogIdx",1)).enqueue(new Callback<MyPageResponse>() {



            @Override
            public void onResponse(Call<MyPageResponse> call, Response<MyPageResponse> response) {

                mRankingResponse=response.body();
                if(response.code() ==200)
                {

                    mypageView.refresh(mRankingResponse.getResult());


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<MyPageResponse> call, Throwable t) {

                System.out.println("왜"+t.getMessage());
            }

        });


    }


}
