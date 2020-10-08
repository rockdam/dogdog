package com.makeus.dogdog.src.joinmember.step2;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step1.Step1Activity;
import com.makeus.dogdog.src.joinmember.step2.interfaces.ShowToastStep2;
import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;
import com.makeus.dogdog.src.joinmember.step3.Step3Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step2Activity extends BaseActivity implements View.OnClickListener, ShowToastStep2 {

    TextView mNextButton, mBackButton;
    DuplicateUserIdService mDuplicateUserIdService;


    TextView warningText;

    ImageView warningImage, edtclear_step;
    EditText mEdit_Input_Text_joinmember;
    String mNickname, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        mNextButton = findViewById(R.id.next_button_step);
        mBackButton = findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mEdit_Input_Text_joinmember = findViewById(R.id.edit_Input_Text_joinmember);
        mBackButton.setOnClickListener(this);
        edtclear_step = findViewById(R.id.edtclear_step);
        warningImage = findViewById(R.id.warning_image_step2);
        warningText = findViewById(R.id.warning_text_step2);

        Intent emailcheckIntent = getIntent();
        if (emailcheckIntent.hasExtra("email")) {
            mEmail = emailcheckIntent.getExtras().getString("nickname");
            Intent nickNameIntent = getIntent();
            if (nickNameIntent.hasExtra("nickname")) {
                mNickname = nickNameIntent.getExtras().getString("nickname");
            }
        }

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

                if (mEdit_Input_Text_joinmember.getText().toString().equals("") || isValidId(mEdit_Input_Text_joinmember.getText().toString())) {

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
                mEmail = editable.toString();
//                mEdit_Input_Text_joinmember.getBackground().setColorFilter(getResources().getColor(R.color.editTextUnderLine),
//                        PorterDuff.Mode.SRC_ATOP);
//                warningText.setTextColor(ContextCompat.getColor(getBaseContext(),
//                        R.color.startwalkingGray));
//                warningImage.setImageResource(R.drawable.warning_off);

            }
        });
    }

    public static boolean isValidId(final String id) {

        Pattern pattern;
        Matcher matcher;
        final String ID_PATTERN = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        pattern = Pattern.compile(ID_PATTERN);
        matcher = pattern.matcher(id);

        return matcher.matches();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton_step:


                Intent intent = new Intent(Step2Activity.this, Step1Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                intent.putExtra("nickname", mNickname);

                overridePendingTransition(0, 0); // finish()시 애니메이션 삭제
                startActivity(intent);
                finish();
                break;
            case R.id.next_button_step:


                if (!isValidId(mEdit_Input_Text_joinmember.getText().toString())) {


                    Toast.makeText(this, "아이디 형식이 맞지 않습니다. \n다시 입력해주세요 :)", Toast.LENGTH_SHORT).show();

                } else {


                    mDuplicateUserIdService = new DuplicateUserIdService(this, mEdit_Input_Text_joinmember.getText().toString());
                    mDuplicateUserIdService.checkDuplicatedId();
                }


                break;


        }

    }


    // 서버 연결 안될 때 호출하는 함수 혹은 토스트 띄워야함 .
    @Override
    public void checkDuplicatId(DuplicateUserIdResponse duplicateUserIdResponse) {

        if (duplicateUserIdResponse.getCode() == 200) {

            Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            intent.putExtra("nickname", mNickname);
            intent.putExtra("email", mEmail);
            startActivity(intent);
            finish();
        } else {

            Toast.makeText(getApplicationContext(), "중복되는 아이디입니다. 다시 입력해주세요 :)", Toast.LENGTH_SHORT).show();

        }
    }
}