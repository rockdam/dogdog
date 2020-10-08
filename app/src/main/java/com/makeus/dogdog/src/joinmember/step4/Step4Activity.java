package com.makeus.dogdog.src.joinmember.step4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step1.Step1Activity;
import com.makeus.dogdog.src.joinmember.step2.Step2Activity;
import com.makeus.dogdog.src.joinmember.step3.Step3Activity;
import com.makeus.dogdog.src.joinmember.step5.Step5Activity;

public class Step4Activity extends BaseActivity implements View.OnClickListener{
    TextView mNextButton,mBackButton;
    TextView warningText;

    ImageView warningImage,edtclear_step;
    EditText mEdit_Input_Text_joinmember;
    String mInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step4);
        mNextButton=findViewById(R.id.next_button_step);
        mBackButton=findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        mEdit_Input_Text_joinmember=findViewById(R.id.edit_Input_Text_joinmember);
        mBackButton.setOnClickListener(this);
        edtclear_step=findViewById(R.id.edtclear_step);
        warningImage=findViewById(R.id.warning_image_step4);
        warningText=findViewById(R.id.warning_text_step4);
        mInput =mEdit_Input_Text_joinmember.getText().toString();
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



                overridePendingTransition(0,0); // finish()시 애니메이션 삭제
                startActivity(back);
                finish();
                break;
            case R.id.next_button_step:
                if(mInput.length()<2)
                {

                    Toast.makeText(this,"2글자 이상 입력해주세요 :)",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Step4Activity.this, Step5Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                    startActivity(intent);
                    finish();
                }
                break;



        }
    }
}