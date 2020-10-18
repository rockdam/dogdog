package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs;

import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.interfaces.AddDogsRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.interfaces.AddDogsView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.interfaces.SelectDogRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.models.AddDogsResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class AdddogsService {


    AddDogsView addDogsView;
    AddDogsResponse addDogsResponse;


    public AdddogsService(AddDogsView addDogsView) {
        this.addDogsView = addDogsView;
    }


    public void refreshHomeView() {

        final AddDogsRetrofitInterface addDogsRetrofitInterface = getRetrofit().create(AddDogsRetrofitInterface.class);


        addDogsRetrofitInterface.refreshInfo().enqueue(new Callback<AddDogsResponse>() {


            @Override
            public void onResponse(Call<AddDogsResponse> call, Response<AddDogsResponse> response) {

                addDogsResponse=response.body();
                if(response.code() ==200)
                {

                    addDogsView.refresh(addDogsResponse.getResult());


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<AddDogsResponse> call, Throwable t) {

            }

        });


    }

    public void updateHomeView(int dogIdx) {

        final SelectDogRetrofitInterface selectDogRetrofitInterface = getRetrofit().create(SelectDogRetrofitInterface.class);


        selectDogRetrofitInterface.refreshInfo(dogIdx).enqueue(new Callback<AddDogsResponse>() {


            @Override
            public void onResponse(Call<AddDogsResponse> call, Response<AddDogsResponse> response) {

                addDogsResponse=response.body();
                if(response.code() ==200)
                {

                    addDogsView.moveHomeFragemnt();


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<AddDogsResponse> call, Throwable t) {

            }

        });


    }

}
