package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs;

import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.interfaces.AddDogsRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.interfaces.UpdateDogView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.models.AddDogInfo;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.models.DogsUpdateResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.interfaces.UpdateUserRetrofitInterface;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class AddDogsService {


    UpdateDogView updateDogView;
    DogsUpdateResponse dogsUpdateResponse;
    AddDogsRetrofitInterface addDogsRetrofitInterfacee;
    public AddDogsService(UpdateDogView updateDogView) {
        this.updateDogView = updateDogView;
    }


    public void addDogService(AddDogInfo addDogs) {

        final AddDogsRetrofitInterface addDogsRetrofitInterfacee = getRetrofit().create(AddDogsRetrofitInterface.class);


        addDogsRetrofitInterfacee.refreshInfo(addDogs).enqueue(new Callback<DogsUpdateResponse>() {


            @Override
            public void onResponse(Call<DogsUpdateResponse> call, Response<DogsUpdateResponse> response) {

                dogsUpdateResponse=response.body();
                if(response.code() ==200)
                {

                    updateDogView.refresh(dogsUpdateResponse.getIsSuccess());


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<DogsUpdateResponse> call, Throwable t) {

            }

        });


    }

}
