package com.makeus.dogdog.src.joinmember.step3repeat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step1.Step1Activity;
import com.makeus.dogdog.src.joinmember.step2.Step2Activity;
import com.makeus.dogdog.src.joinmember.step3.Step3Activity;
import com.makeus.dogdog.src.joinmember.step4.Step4Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step3ReeatActivity extends BaseActivity implements View.OnClickListener {
    TextView mNextButton,mBackButton;
    TextView warningText;

    ImageView warningImage,edtclear_step;
    EditText mEdit_Input_Text_joinmember;
    String mInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3_repeat);
        mNextButton=findViewById(R.id.next_button_step);
        mBackButton=findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        mEdit_Input_Text_joinmember=findViewById(R.id.edit_Input_Text_joinmember);
        mBackButton.setOnClickListener(this);
        edtclear_step=findViewById(R.id.edtclear_step);
        warningImage=findViewById(R.id.warning_image_step3repeat);
        warningText=findViewById(R.id.warning_text_step3repeat);
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

                if (mEdit_Input_Text_joinmember.getText().toString().equals("") || isValidPassword(mEdit_Input_Text_joinmember.getText().toString())) {

                    mEdit_Input_Text_joinmember.getBackground().setColorFilter(getResources().getColor(R.color.startwalkingGray),
                            PorterDuff.Mode.SRC_ATOP);
                    warningText.setTextColor(ContextCompat.getColor(getBaseContext(),
                            R.color.startwalkingGray));
                    warningImage.setImageResource(R.drawable.warning_off);

                } else {


                    mEdit_Input_Text_joinmember.getBackground().setColorFilter(getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);

                    warningText.setTextColor(ContextCompat.getColor(getBaseContext(),
                            R.color.warningRed));
                    warningImage.setImageResource(R.drawable.warning_on);
//                    https://stackoverflow.com/questions/28303112/changing-tint-color-of-android-edittext-programmatically
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
                Intent back = new Intent(Step3ReeatActivity.this, Step3Activity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0); // finish()시 애니메이션 삭제
                startActivity(back);
                finish();
                break;
            case R.id.next_button_step:
                if (!isValidPassword(mEdit_Input_Text_joinmember.getText().toString())) {
                    Toast.makeText(this, "패스워드 형식이 맞지 않습니다. \n다시 입력해주세요 :)", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Step3ReeatActivity.this, Step4Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                    startActivity(intent);
                    finish();
                }
                break;



        }
    }
}