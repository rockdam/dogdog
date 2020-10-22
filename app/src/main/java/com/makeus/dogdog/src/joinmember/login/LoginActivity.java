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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements View.OnClickListener{


    TextView mJoinmemberButton;
    AutoLoginService mAutoLoginService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join);

        mJoinmemberButton=findViewById(R.id.joinmember_acitivity_join);

        mJoinmemberButton.setOnClickListener(this);


    }
    public static boolean isValidPassword(final String Password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{6,16}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(Password);

        return matcher.matches();

    }


    @Override
    public void onClick(View view) {


        switch(view.getId())
        {

            case R.id.joinmember_acitivity_join:

                Intent intent =new Intent(LoginActivity.this, Step1Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent);
                finish();
                //이건 좀 생각해보자 .;
                break;


        }

    }


}