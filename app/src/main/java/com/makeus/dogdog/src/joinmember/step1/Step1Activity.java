package com.makeus.dogdog.src.joinmember.step1;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.login.LoginActivity;
import com.makeus.dogdog.src.joinmember.step2.Step2Activity;
import com.makeus.dogdog.src.joinmember.step6.models.userInfo;

public class Step1Activity extends BaseActivity implements View.OnClickListener {


    TextView mJoinMessage, mNextTxt, warningText;

    ImageView warningImage,edtclear_step;
    EditText mEdit_Input_Text_joinmember;
    String mNickName;
    ImageView backStep;

    userInfo mUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);
        mJoinMessage = findViewById(R.id.tellmePassword_Step2Activity);
        mEdit_Input_Text_joinmember = findViewById(R.id.edit_Input_Text_joinmember);
        mNextTxt = findViewById(R.id.next_button_step);
        mJoinMessage.setText(Html.fromHtml("<b>" + "만나서 반가워요!" + "<br>" + "당신의 이름" + "</br>" + "</b>" + "이 궁금해요."));
        warningText = findViewById(R.id.warning_text_step);

        warningImage = findViewById(R.id.warning_image_step);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.spoqahansregular);
        warningText.setTypeface(typeface);
        edtclear_step=findViewById(R.id.edtclear_step);
        mUserInfo=new userInfo();
        mNextTxt.setOnClickListener(this);
        //  mJoinMessage.setTypeface(typeface);
        //깃 연동 기념
        //슬랙 연동 기념

        Intent intent = getIntent();
        if(intent.hasExtra("userInfo")) {

            mUserInfo =(userInfo)intent.getSerializableExtra("userInfo");




            if(mUserInfo.getNickName()!=null) {
                mEdit_Input_Text_joinmember.setText(mUserInfo.getNickName());
                Log.e("뭔데", mUserInfo.getNickName());
            }
            }

        backStep=findViewById(R.id.back_step1);
        backStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Step1Activity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent);
                finish();



            }
        });

    }

//https://gun0912.tistory.com/56 나중에 이걸로 연습해보자.
    @Override
    protected void onResume() {
        super.onResume();
//        https://stackoverflow.com/questions/476848/android-textwatcher-aftertextchanged-vs-textwatcher-ontextchanged 설명
//        https://lktprogrammer.tistory.com/185
        edtclear_step.setOnClickListener(view -> {

            mEdit_Input_Text_joinmember.setText("");

        });
        mEdit_Input_Text_joinmember.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                mNickName =mEdit_Input_Text_joinmember.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                mNickName =mEdit_Input_Text_joinmember.getText().toString();
                if (mEdit_Input_Text_joinmember.getText().toString().length() == 1) {

                    mEdit_Input_Text_joinmember.getBackground().setColorFilter(getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);

                    warningText.setTextColor(ContextCompat.getColor(getBaseContext(),
                            R.color.warningRed));
                    warningImage.setImageResource(R.drawable.warning_on);
//                    https://stackoverflow.com/questions/28303112/changing-tint-color-of-android-edittext-programmatically

                } else {
                    mEdit_Input_Text_joinmember.getBackground().setColorFilter(getResources().getColor(R.color.startwalkingGray),
                            PorterDuff.Mode.SRC_ATOP);
                    warningText.setTextColor(ContextCompat.getColor(getBaseContext(),
                            R.color.startwalkingGray));
                    warningImage.setImageResource(R.drawable.warning_off);


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                mEdit_Input_Text_joinmember.getBackground().setColorFilter(getResources().getColor(R.color.editTextUnderLine),
//                        PorterDuff.Mode.SRC_ATOP);
//                warningText.setTextColor(ContextCompat.getColor(getBaseContext(),
//                        R.color.startwalkingGray));
//                warningImage.setImageResource(R.drawable.warning_off);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.next_button_step:
                if(mEdit_Input_Text_joinmember.getText().toString().length()<2)
                {

                    Toast.makeText(this,"2글자 이상 입력해주세요 :)",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Step1Activity.this, Step2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    //Edittext 비었을 때 읽으면 null
                    if(mEdit_Input_Text_joinmember.getText().toString()==null) {
                        mUserInfo.setNickName("");
                    }else{

                        mUserInfo.setNickName(mEdit_Input_Text_joinmember.getText().toString());
                    }
//                   이게 이렇게 짤 수 밖에 없네 . 변화가 안 일어 난 상태에서 변수 보내면 null

                    intent.putExtra("userInfo", mUserInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivity(intent);
                    finish();


                }

                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(Step1Activity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);
        finish();
    }
}