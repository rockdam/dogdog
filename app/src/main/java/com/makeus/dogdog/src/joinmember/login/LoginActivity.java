package com.makeus.dogdog.src.joinmember.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.HomeActivity;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;
import com.makeus.dogdog.src.joinmember.step1.Step1Activity;

public class LoginActivity extends BaseActivity implements View.OnClickListener, MoveHomeAcitivity {


    TextView mJoinmemberButton;
    AutoLoginService mAutoLoginService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join);

        mJoinmemberButton=findViewById(R.id.joinmember_acitivity_join);

        mJoinmemberButton.setOnClickListener(this);

        AutoLogin();
    }

    void AutoLogin()
    {


        mAutoLoginService=new AutoLoginService(this);
        mAutoLoginService.checkAutoLogin();


    }
    @Override
    public void onClick(View view) {


        switch(view.getId())
        {

            case R.id.joinmember_acitivity_join:

                Intent intent =new Intent(LoginActivity.this, Step1Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent);
                break;


        }

    }

    @Override
    public void move() {
        Intent intent =new Intent(LoginActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);
        finish();
    }
}