package com.makeus.dogdog.src.joinmember.step2;

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
import com.makeus.dogdog.src.joinmember.step2.interfaces.ShowToastStep2;
import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;
import com.makeus.dogdog.src.joinmember.step3.Step3Activity;

public class Step2Activity extends BaseActivity implements View.OnClickListener, ShowToastStep2 {

    TextView mNextButton,mBackButton;
    DuplicateUserIdService mDuplicateUserIdService;



    TextView warningText;

    ImageView warningImage,edtclear_step;
    EditText mEdit_Input_Text_joinmember;
    String mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        mNextButton=findViewById(R.id.next_button_step);
        mBackButton=findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mEdit_Input_Text_joinmember=findViewById(R.id.edit_Input_Text_joinmember);
        mBackButton.setOnClickListener(this);
        edtclear_step=findViewById(R.id.edtclear_step);
        warningImage=findViewById(R.id.warning_image_step2);
        warningText=findViewById(R.id.warning_text_step2);

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
                if (mInput.length() >0 && mInput.length() <5) {

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
                finish();
                overridePendingTransition(0,0); // finish()시 애니메이션 삭제

                break;
            case R.id.next_button_step:


                if (mInput.length() >0 && mInput.length() <5) {


                    mDuplicateUserIdService =new DuplicateUserIdService(this,mEdit_Input_Text_joinmember.getText().toString());
                    mDuplicateUserIdService.checkDuplicatedId();

                }else{

                    Toast.makeText(this,"5글자 이상 입력해주세요 :)",Toast.LENGTH_SHORT);
                }


                break;



        }

    }

    @Override
    public void checkDuplicatId(DuplicateUserIdResponse duplicateUserIdResponse) {

        if(duplicateUserIdResponse.getCode()==200)
        {

            Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


            startActivity(intent);
        }else{

            Toast.makeText(getApplicationContext(),"중복되는 아이디입니다. 다시 입력해주세요 :)",Toast.LENGTH_SHORT).show();

        }
    }
}