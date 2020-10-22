package com.makeus.dogdog.src.joinmember.login;

import android.util.Log;

import com.makeus.dogdog.src.joinmember.login.interfaces.LoginAcitivityRetrofitInterface;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;
import com.makeus.dogdog.src.joinmember.login.models.AutoLoginResponse;
import com.makeus.dogdog.src.joinmember.step2.interfaces.ShowToastStep2;
import com.makeus.dogdog.src.joinmember.step2.interfaces.Step2RetrofitInterface;
import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class AutoLoginService {


    MoveHomeAcitivity mMoveHomeAcitivity;
    String mUserId;

    AutoLoginResponse autoLoginResponse;
    public AutoLoginService(MoveHomeAcitivity moveHomeAcitivity) {
        this.mMoveHomeAcitivity = moveHomeAcitivity;
    }


    public void checkAutoLogin() {

        final LoginAcitivityRetrofitInterface loginAcitivityRetrofitInterface = getRetrofit().create(LoginAcitivityRetrofitInterface.class);


        loginAcitivityRetrofitInterface.checkId().enqueue(new Callback<AutoLoginResponse>() {


            @Override
            public void onResponse(Call<AutoLoginResponse> call, Response<AutoLoginResponse> response) {

                autoLoginResponse=response.body();
                if(response.code() ==200)
                {

                    mMoveHomeAcitivity. move(response.code());



                }else{
                    mMoveHomeAcitivity.move(response.code());

                }

                // 자동로그인 안되면 로그인 해야지 .




            }

            @Override
            public void onFailure(Call<AutoLoginResponse> call, Throwable t) {

            }

        });


    }

}
