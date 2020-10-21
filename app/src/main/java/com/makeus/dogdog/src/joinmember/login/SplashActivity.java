package com.makeus.dogdog.src.joinmember.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.HomeActivity;
import com.makeus.dogdog.src.joinmember.login.LoginActivity;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;

public class SplashActivity  extends BaseActivity implements MoveHomeAcitivity {
    AutoLoginService mAutoLoginService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AutoLogin();


    }
    void AutoLogin()
    {


        mAutoLoginService=new AutoLoginService(this);
        mAutoLoginService.checkAutoLogin();


    }

    @Override
    public void move(int code) {
        if (code == 200) {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(intent);
            finish();

        }
    }
}
