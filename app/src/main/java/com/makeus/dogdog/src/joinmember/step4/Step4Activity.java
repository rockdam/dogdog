package com.makeus.dogdog.src.joinmember.step4;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
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

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step3.Step3Activity;
import com.makeus.dogdog.src.joinmember.step5.Step5Activity;
import com.makeus.dogdog.src.joinmember.step6.models.dogInfo;
import com.makeus.dogdog.src.joinmember.step6.models.userInfo;

public class Step4Activity extends BaseActivity implements View.OnClickListener{
    TextView mNextButton,mBackButton,tellmePassword;
    TextView warningText;

    ImageView warningImage,edtclear_step,back_step4;
    EditText mEdit_Input_Text_joinmember;
    String mInput;
    dogInfo mDogInfo;
    userInfo mUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step4);
        mNextButton=findViewById(R.id.next_button_step);
        mBackButton=findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        tellmePassword=findViewById(R.id.tellmePassword_Step2Activity);
        tellmePassword.setText(Html.fromHtml(  "<b>"+ "당신의 반려견의" +"</b>"+"<br>" + "</br>"+"<b>"+"이름"+"</b>"+"은 무엇인가요?"));
        mDogInfo=new dogInfo();
        mUserInfo=new userInfo();
        mEdit_Input_Text_joinmember=findViewById(R.id.edit_Input_Text_joinmember);
        mBackButton.setOnClickListener(this);
        edtclear_step=findViewById(R.id.edtclear_step);
        warningImage=findViewById(R.id.warning_image_step4);
        warningText=findViewById(R.id.warning_text_step4);

        Intent intent = getIntent();
        if (intent.hasExtra("userInfo") ) {

            mUserInfo = (userInfo) intent.getSerializableExtra("userInfo");

        }
        if(intent.hasExtra("dogInfo"))
        {

            mDogInfo  =(dogInfo)intent.getSerializableExtra("dogInfo");
            if(mDogInfo.getName()!=null)
                mEdit_Input_Text_joinmember.setText(mDogInfo.getName());
        }

        back_step4=findViewById(R.id.back_step4);
        back_step4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Step4Activity.this, Step3Activity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                back.putExtra("userInfo", mUserInfo);

                Log.e("뭐여",mUserInfo.getPassword());
                overridePendingTransition(0,0); // finish()시 애니메이션 삭제
                startActivity(back);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent back = new Intent(Step4Activity.this, Step3Activity.class);
        back.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


        back.putExtra("userInfo", mUserInfo);

        Log.e("뭐여",mUserInfo.getPassword());
        overridePendingTransition(0,0); // finish()시 애니메이션 삭제
        startActivity(back);
        finish();
    }

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

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                mInput =mEdit_Input_Text_joinmember.getText().toString();
                if (mInput.length() == 1) {

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
        switch (view.getId())
        {
            case R.id.backButton_step:

                Intent back = new Intent(Step4Activity.this, Step3Activity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                back.putExtra("userInfo", mUserInfo);

                Log.e("뭐여",mUserInfo.getPassword());
                overridePendingTransition(0,0); // finish()시 애니메이션 삭제
                startActivity(back);
                finish();
                break;
            case R.id.next_button_step:
                if(mEdit_Input_Text_joinmember.getText()!=null && mEdit_Input_Text_joinmember.getText().toString().length()<2)
                {

                    Toast.makeText(this,"2글자 이상 입력해주세요 :)",Toast.LENGTH_SHORT).show();
                }else {

                    if(mEdit_Input_Text_joinmember.getText().toString()==null) {
                        mDogInfo.setName("");
                    }else{

                        mDogInfo.setName(mEdit_Input_Text_joinmember.getText().toString());
                    }
                    Intent intent = new Intent(Step4Activity.this, Step5Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("dogInfo",mDogInfo);

                    intent.putExtra("userInfo", mUserInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
                break;



        }
    }
}