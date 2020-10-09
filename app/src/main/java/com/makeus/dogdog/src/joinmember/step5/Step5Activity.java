package com.makeus.dogdog.src.joinmember.step5;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step3.Step3Activity;
import com.makeus.dogdog.src.joinmember.step4.Step4Activity;
import com.makeus.dogdog.src.joinmember.step6.Step6Activity;
import com.makeus.dogdog.src.joinmember.step6.models.DogInfo;
import com.makeus.dogdog.src.joinmember.step6.models.UserInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Step5Activity extends BaseActivity implements View.OnClickListener {
    TextView mTellUsAge, mBackButton;
    TextView mJoinMessage, mNextButton;
    EditText mEdit_Input_Text_joinmember;
    ImageView edtclear_step;
    DogInfo mDogInfo;
    UserInfo mUserInfo;
    RadioGroup mRgGender;
    RadioButton mMale,mFemale;
    String mGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step5);
        mTellUsAge = findViewById(R.id.tellUsAge_Step2Activity);
        mTellUsAge.setText(Html.fromHtml("<b>" + "반려견의 성별과 나이" + "</b>" + "를" + "<br>" + "</br>" + "알려주세요."));
        mUserInfo=new UserInfo();
        mDogInfo =new DogInfo();
        mBackButton = findViewById(R.id.backButton_step);
        mBackButton.setOnClickListener(this);
        mNextButton=findViewById(R.id.next_button_step);
        mNextButton.setOnClickListener(this);
        mEdit_Input_Text_joinmember=findViewById(R.id.edit_Input_Text_joinmember);
        edtclear_step=findViewById(R.id.edtclear_step);
        mRgGender=findViewById(R.id.rgGender);
        mMale=findViewById(R.id.male_step5);
        mFemale=findViewById(R.id.female_step5);



        Intent intent = getIntent();
        if (intent.hasExtra("userInfo") ) {

            mUserInfo = (UserInfo) intent.getSerializableExtra("userInfo");

        }
        if(intent.hasExtra("dogInfo"))
        {

            mDogInfo  =(DogInfo)intent.getSerializableExtra("dogInfo");
            if(mDogInfo.getBirth()!=null)
                mEdit_Input_Text_joinmember.setText(mDogInfo.getBirth());

            if(mDogInfo.getGender()!=null) {

               mGender= mDogInfo.getGender();
                if (mDogInfo.getGender().equals("male")) {
                    mMale.setChecked(true);

                } else {

                    mFemale.setChecked(true);
                }
            }
        }


        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i == R.id.male_step5)
                {
                    mGender="male";


                }
                if(i == R.id.female_step5)
                {
                    mGender="female";

                }
            }
        });

    }
// 유효한 날짜인지 체크
    public  boolean  validationDate(String  checkDate){

        try{
            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");

            dateFormat.setLenient(false);
            dateFormat.parse(checkDate);
            return  true;

        }catch (ParseException e){
            return  false;
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        edtclear_step.setOnClickListener(view -> {

            mEdit_Input_Text_joinmember.setText("");

        });
        mEdit_Input_Text_joinmember.addTextChangedListener(new TextWatcher() {

            private int _beforeLenght = 0;
            private int _afterLenght = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _beforeLenght = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Length)");
                    return;
                }

                char inputChar = s.charAt(s.length() - 1);
                if (inputChar != '-' && (inputChar < '0' || inputChar > '9')) {
                    mEdit_Input_Text_joinmember.getText().delete(s.length() - 1, s.length());
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Number)");
                    return;
                }

                _afterLenght = s.length();

                // 삭제 중
                if (_beforeLenght > _afterLenght) {
                    // 삭제 중에 마지막에 -는 자동으로 지우기
                    if (s.toString().endsWith("-")) {
                        mEdit_Input_Text_joinmember.setText(s.toString().substring(0, s.length() - 1));
                    }
                }
                // 입력 중
                else if (_beforeLenght < _afterLenght) {
                    if (_afterLenght == 5 && s.toString().indexOf("-") < 0) {
                        mEdit_Input_Text_joinmember.setText(s.toString().subSequence(0, 4) + "-" + s.toString().substring(4, s.length()));
                    } else if (_afterLenght == 7) {
                        mEdit_Input_Text_joinmember.setText(s.toString().subSequence(0, 7) + "-" + s.toString().substring(7, s.length()));
                    }
                }
                mEdit_Input_Text_joinmember.setSelection(mEdit_Input_Text_joinmember.length());
//
//                if(s.length() == 19) {
//                    btnInput.setBackground(
//                            ContextCompat.getDrawable(getContext(), R.drawable.btn_active));
//                } else {
//                    btnInput.setBackground(
//                            ContextCompat.getDrawable(getContext(), R.drawable.btn_inactive));
//                }
//                글자가 19개 일 때 버튼이 활성화 되는 코드
//
//                출처: https://devvkkid.tistory.com/111 [개발자입니까?]
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 생략
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton_step:
                Intent back = new Intent(Step5Activity.this, Step4Activity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                back.putExtra("dogInfo", mDogInfo);
                back.putExtra("userInfo", mUserInfo);
                overridePendingTransition(0,0); // finish()시 애니메이션 삭제
                startActivity(back);
                finish();
                break;
            case R.id.next_button_step:

                if(validationDate(mEdit_Input_Text_joinmember.getText().toString())) {
                    Intent intent = new Intent(Step5Activity.this, Step6Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    if(mEdit_Input_Text_joinmember.getText().toString()==null) {
                        mDogInfo.setBirth("");
                    }else{

                        mDogInfo.setBirth(mEdit_Input_Text_joinmember.getText().toString());
                    }
                    if(mGender.equals("male"))
                    {
                        mDogInfo.setGender("male");
                    }else{

                        mDogInfo.setGender("female");
                    }
                    intent.putExtra("dogInfo",mDogInfo);

                    intent.putExtra("userInfo", mUserInfo);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this,"유효한 날짜 형식이 아닙니다.",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}