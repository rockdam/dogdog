package com.makeus.dogdog.src.joinmember.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.HomeActivity;
import com.makeus.dogdog.src.joinmember.login.interfaces.LoginMoveHomeActivity;
import com.makeus.dogdog.src.joinmember.login.interfaces.MoveHomeAcitivity;
import com.makeus.dogdog.src.joinmember.login.models.LogInResponse;
import com.makeus.dogdog.src.joinmember.login.models.LoginBody;
import com.makeus.dogdog.src.joinmember.step1.Step1Activity;
import com.makeus.dogdog.src.joinmember.step2.DuplicateUserIdService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginMoveHomeActivity {


    TextView mJoinmemberButton,mLogin;
    EditText mInputEmail,mInputPassword;
    LoginBody mLoginBody;
    LoginService loginService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join);

        mJoinmemberButton=findViewById(R.id.joinmember_acitivity_join);

        mJoinmemberButton.setOnClickListener(this);
        mLogin=findViewById(R.id.login_activity_join);
        mInputEmail =findViewById(R.id.id_login);
        mInputPassword=findViewById(R.id.password_login);

        mLoginBody=new LoginBody();
        mLogin.setOnClickListener(this);


    }
    public static boolean isValidPassword(final String Password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{6,16}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(Password);

        return matcher.matches();

    }
    //    https://stackoverflow.com/questions/12947620/email-address-validation-in-android-on-edittext
//    이메일 체크는 이걸로하면됌
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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
                break;
            case R.id.login_activity_join:


                if (!isValidEmail(mInputEmail.getText().toString())) {


                    Toast.makeText(this, "이메일 형식이 맞지 않습니다. \n다시 입력해주세요 :)", Toast.LENGTH_SHORT).show();

                }else if( !isValidPassword(mInputPassword.getText().toString()))
                {


                    Toast.makeText(this, "패스워드 형식이 맞지 않습니다. \n다시 입력해주세요 :)", Toast.LENGTH_SHORT).show();
                }


                else {

//서비스


                    mLoginBody.setEmail(mInputEmail.getText().toString());
                    mLoginBody.setPassword(mInputPassword.getText().toString());
                    loginService=new LoginService(this,mLoginBody);
                    loginService.startLogin();
                }


                break;




        }

    }




    @Override
    public void move(LogInResponse logInResponse) {
        if (logInResponse.getCode() == 200) {


            SharedPreferences.Editor editor=sSharedPreferences.edit();
            editor.putString("X-ACCESS-TOKEN",logInResponse.getJwt());

            editor.apply();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();

        }
    }
}