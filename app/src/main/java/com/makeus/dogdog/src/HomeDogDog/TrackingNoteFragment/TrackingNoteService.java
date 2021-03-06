package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment;

import android.util.Log;

import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models.WalkinghistoryResponse;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces.DayWalkingInfoRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces.GetWalkingHistoryRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces.TrackingNoteRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces.TrackingNoteView;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.GetWalkinghistoryResponse;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingMonthResponse;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingdayResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;
import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;

public class TrackingNoteService {

    TrackingNoteView trackingNoteView;
    TrackingNoteRetrofitInterface trackingNoteRetrofitInterface;
    WalkingMonthResponse walkingMonthResponse;
    WalkingdayResponse walkingdayResponse;
    GetWalkinghistoryResponse getWalkinghistoryResponse;
    String date;
    int dogIdx;

    public TrackingNoteService(TrackingNoteView trackingNoteView, String date) {
        this.trackingNoteView = trackingNoteView;
        this.date = date;
        dogIdx=sSharedPreferences.getInt("dogIdx", 0);
    }




    public void refreshUpdateWalkingMonth() {

        final  TrackingNoteRetrofitInterface trackingNoteRetrofitInterface = getRetrofit().create(TrackingNoteRetrofitInterface.class);


        trackingNoteRetrofitInterface.walkingMonth(date,dogIdx).enqueue(new Callback<WalkingMonthResponse>() {


            @Override
            public void onResponse(Call<WalkingMonthResponse> call, Response<WalkingMonthResponse> response) {

                walkingMonthResponse = response.body();
                if (response.code() == 200) {

                    trackingNoteView.updateMonth(walkingMonthResponse.getResult());


                }// 자동로그인 안되면 로그인 해야지 .


            }


            @Override
            public void onFailure(Call<WalkingMonthResponse> call, Throwable t) {

                Log.e("뭐가","문제여");
            }

        });


    }


    public void refreshUpdateWalkingDay() {

        final  DayWalkingInfoRetrofitInterface dayWalkingInfoRetrofitInterface = getRetrofit().create(DayWalkingInfoRetrofitInterface.class);


        dayWalkingInfoRetrofitInterface.walkingDay(date,dogIdx).enqueue(new Callback<WalkingdayResponse>() {


            @Override
            public void onResponse(Call<WalkingdayResponse> call, Response<WalkingdayResponse> response) {

                walkingdayResponse = response.body();
                if (response.code() == 200) {

                    trackingNoteView.updateDay(walkingdayResponse.getResult());


                }// 자동로그인 안되면 로그인 해야지 .


            }


            @Override
            public void onFailure(Call<WalkingdayResponse> call, Throwable t) {

                Log.e("뭐가","문제여");
            }

        });


    }

    public void initializeWalkingDay() {

        final GetWalkingHistoryRetrofitInterface getWalkingHistoryRetrofitInterface = getRetrofit().create(GetWalkingHistoryRetrofitInterface.class);


        getWalkingHistoryRetrofitInterface.walkingDay(date,dogIdx).enqueue(new Callback<GetWalkinghistoryResponse>() {


            @Override
            public void onResponse(Call<GetWalkinghistoryResponse> call, Response<GetWalkinghistoryResponse> response) {

                getWalkinghistoryResponse = response.body();
                if (response.code() == 200) {


                    try {
                        trackingNoteView.initialTackingNot(getWalkinghistoryResponse.getResult());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }// 자동로그인 안되면 로그인 해야지 .


            }


            @Override
            public void onFailure(Call<GetWalkinghistoryResponse> call, Throwable t) {

                Log.e("뭐가","문제여");
            }

        });


    }



}
