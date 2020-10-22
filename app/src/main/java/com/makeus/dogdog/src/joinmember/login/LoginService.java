package com.makeus.dogdog.src.joinmember.login;

import android.widget.Toast;

import com.makeus.dogdog.src.joinmember.login.interfaces.LoginAcitivityRetrofitInterface;
import com.makeus.dogdog.src.joinmember.login.interfaces.LoginMoveHomeActivity;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;
import com.makeus.dogdog.src.joinmember.login.models.AutoLoginResponse;
import com.makeus.dogdog.src.joinmember.login.models.LoginBody;
import com.makeus.dogdog.src.joinmember.login.models.LogInResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.dogdog.src.ApplicationClass.getRetrofit;

public class LoginService {


    LoginMoveHomeActivity loginMoveHomeActivity;
    LoginBody loginBody;
    LogInResponse logInResponse;
    public LoginService(LoginMoveHomeActivity loginMoveHomeActivity, LoginBody loginBody) {
        this.loginMoveHomeActivity = loginMoveHomeActivity;
        this.loginBody=loginBody;
    }


    public void startLogin() {

        final LoginAcitivityRetrofitInterface loginAcitivityRetrofitInterface = getRetrofit().create(LoginAcitivityRetrofitInterface.class);


        loginAcitivityRetrofitInterface.checkBody(loginBody).enqueue(new Callback<LogInResponse>() {


            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {

                logInResponse =response.body();
                if(response.code() ==200)
                {

                    loginMoveHomeActivity.move(logInResponse);



                }else{
//                    mMoveHomeAcitivity.move(response.code());

                }

                // 자동로그인 안되면 로그인 해야지 .




            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {

            }

        });


    }

}
