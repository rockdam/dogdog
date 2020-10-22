package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote;

import android.util.Log;

import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.interfaces.FinishCallback;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.interfaces.UpdateWalkingHistoryRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.interfaces.WalkingHistoryRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models.DayHistory;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models.WalkinghistoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class AddTrackingNoteService {



    WalkingHistoryRetrofitInterface walkingHistoryRetrofitInterface;
    FinishCallback finishCallback;
    WalkinghistoryResponse WalkinghistoryResponse;
    public AddTrackingNoteService(FinishCallback finishCallback) {
        this.finishCallback = finishCallback;
    }


    public void createWalkingNoteHistory(DayHistory dayHistory) {

        final WalkingHistoryRetrofitInterface walkingHistoryRetrofitInterface = getRetrofit().create(WalkingHistoryRetrofitInterface.class);


        walkingHistoryRetrofitInterface.walkingDay(dayHistory).enqueue(new Callback<WalkinghistoryResponse>() {


            @Override
            public void onResponse(Call<WalkinghistoryResponse> call, Response<WalkinghistoryResponse> response) {

                WalkinghistoryResponse=response.body();
                if(response.code() ==200)
                {

                    Log.e("오류췍",response.message());
                    finishCallback.finishActivity();



                }else{
//                    finishCallback.finishActivity(response.code());
                    Log.e("산책 기록 생성 리퀘스트 코드는?",""+response.code());

                }

                // 자동로그인 안되면 로그인 해야지 .




            }

            @Override
            public void onFailure(Call<WalkinghistoryResponse> call, Throwable t) {

            }

        });


    }
    public void updateWalkingNoteHistory(DayHistory dayHistory) {

        final UpdateWalkingHistoryRetrofitInterface updateWalkingHistoryRetrofitInterface = getRetrofit().create(UpdateWalkingHistoryRetrofitInterface.class);


        updateWalkingHistoryRetrofitInterface.updateWalkingHistory(dayHistory).enqueue(new Callback<WalkinghistoryResponse>() {


            @Override
            public void onResponse(Call<WalkinghistoryResponse> call, Response<WalkinghistoryResponse> response) {

                WalkinghistoryResponse=response.body();
                if(response.code() ==200)
                {

                    Log.e("오류췍",response.message());
                    finishCallback.finishActivity();



                }else{
//                    finishCallback.finishActivity(response.code());
                    Log.e("산책 기록 생성 리퀘스트 코드는?",""+response.code());

                }

                // 자동로그인 안되면 로그인 해야지 .




            }

            @Override
            public void onFailure(Call<WalkinghistoryResponse> call, Throwable t) {

            }

        });


    }

}
