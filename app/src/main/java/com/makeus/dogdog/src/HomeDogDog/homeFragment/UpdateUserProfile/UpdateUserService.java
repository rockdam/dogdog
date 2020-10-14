package com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile;

import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.interfaces.UpdateUserRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.interfaces.UpdateUserView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.models.UserUpdateResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshRetrofitInterface;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class UpdateUserService {


    MoveHomeAcitivity mMoveHomeAcitivity;
    UpdateUserView updateUserView;
    UserUpdateResponse userUpdateResponse;
    UpdateUserRetrofitInterface updateUserRetrofitInterface;
    public UpdateUserService(UpdateUserView updateUserView) {
        this.updateUserView = updateUserView;
    }


    public void refreshUpdateUser(int dogIdx) {

        final UpdateUserRetrofitInterface updateUserRetrofitInterface = getRetrofit().create(UpdateUserRetrofitInterface.class);


        updateUserRetrofitInterface.refreshInfo(dogIdx).enqueue(new Callback<UserUpdateResponse>() {


            @Override
            public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {

                userUpdateResponse=response.body();
                if(response.code() ==200)
                {

                    updateUserView.refresh(userUpdateResponse.getResult());


                }// 자동로그인 안되면 로그인 해야지 .




            }



            @Override
            public void onFailure(Call<UserUpdateResponse> call, Throwable t) {

            }

        });


    }

}
