package com.makeus.dogdog.src.joinmember.step6;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step5.Step5Activity;
import com.makeus.dogdog.src.joinmember.step6.interfaces.MoveAcitivity7Interface;
import com.makeus.dogdog.src.joinmember.step6.models.PostJoinMember;
import com.makeus.dogdog.src.joinmember.step6.models.dogInfo;
import com.makeus.dogdog.src.joinmember.step6.models.userInfo;
import com.makeus.dogdog.src.joinmember.step7.Step7Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step6Activity extends BaseActivity implements View.OnClickListener, MoveAcitivity7Interface {
    TextView mJoinMessage, mNextButton, mBackButton, dog_breeds_step6;
    EditText mEdit_Input_Text_joinmember;
    String mKg;
    SearchBreedsDialog mSearchBreedsDialog;
    dogInfo mDogInfo;
    int mBreedsIdx;
    userInfo mUserInfo;
    float mWeight = 0;
    Step6Service mStep6Service;
    ImageView edtclear_step;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step6);
        mNextButton = findViewById(R.id.next_button_step);
        mBackButton = findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        edtclear_step=findViewById(R.id.edtclear_step);
        mUserInfo = new userInfo();
        mDogInfo = new dogInfo();
        dog_breeds_step6 = findViewById(R.id.dog_breeds_step6);

        mEdit_Input_Text_joinmember = findViewById(R.id.edit_Input_Text_joinmember);
        mSearchBreedsDialog = new SearchBreedsDialog(this);

        mEdit_Input_Text_joinmember.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mKg = mEdit_Input_Text_joinmember.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (isValidKg(editable.toString())) {
                    mWeight = Float.parseFloat(mKg);
                    mKg += " kg";

                    mEdit_Input_Text_joinmember.setText(mKg);
                    mEdit_Input_Text_joinmember.setSelection(mEdit_Input_Text_joinmember.getText().length() - 3);


                }


            }
        });
        edtclear_step.setOnClickListener(view -> {

            mEdit_Input_Text_joinmember.setText("");

        });
        dog_breeds_step6.setOnClickListener(view -> {

            mSearchBreedsDialog = new SearchBreedsDialog(this);
            mSearchBreedsDialog.setDialogListener((breed, breedIdx) -> {
                        dog_breeds_step6.setText(breed);
                        mBreedsIdx = breedIdx;
                    }
//


            );

            mSearchBreedsDialog.show();


        });
        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {

            mUserInfo = (userInfo) intent.getSerializableExtra("userInfo");

        }
        if (intent.hasExtra("dogInfo")) {

            mDogInfo = (dogInfo) intent.getSerializableExtra("dogInfo");

        }

    }

    public static boolean isValidKg(final String Password) {

        Pattern pattern;
        Matcher matcher;

        final String KG_PATTERN = "^(0|[1-9]\\d*)(\\.\\d+)?$";
        pattern = Pattern.compile(KG_PATTERN);
        matcher = pattern.matcher(Password);

        return matcher.matches();

    }
    boolean checkCorrectWeightValue() {

        boolean returnValue;
        String check = mEdit_Input_Text_joinmember.getText().toString();
        int idx = check.indexOf("k");
        if (idx == -1) {
            returnValue = true;
        } else {

            String checkdf=check.substring(0, idx);


            if (isValidKg(check.substring(0, idx))) {
                mWeight = Float.parseFloat(check.substring(0, idx));
                returnValue=false;
            }else{
                returnValue=true;

            }
        }

        return returnValue;
    }

    @Override
    protected void onResume() {
        super.onResume();


//        https://stackoverflow.com/questions/2811031/decimal-or-numeric-values-in-regular-expression-validation 출처
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton_step:
                Intent back = new Intent(Step6Activity.this, Step5Activity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                back.putExtra("dogInfo", mDogInfo);
                back.putExtra("userInfo", mUserInfo);

                overridePendingTransition(0, 0); // finish()시 애니메이션 삭제
                startActivity(back);
                finish();
                break;
            case R.id.next_button_step:
                if (mEdit_Input_Text_joinmember.getText().toString() == null) {
                    Toast.makeText(getBaseContext(),"몸무게를 입력해주세요",Toast.LENGTH_LONG);
                }else if(checkCorrectWeightValue() || mWeight <= 0)
                {

                    Toast.makeText(getBaseContext(),"정확한 몸무게를  입력해주세요",Toast.LENGTH_LONG);
                }

                else {

                    mDogInfo.setWeight(mWeight);
                    mDogInfo.setBreedIdx(mBreedsIdx);

                    PostJoinMember postJoinMember = new PostJoinMember();

                    postJoinMember.setDogInfo(mDogInfo);
                    postJoinMember.setUserInfo(mUserInfo);
                    mStep6Service = new Step6Service(this);
                    mStep6Service.postJoinMember(postJoinMember);
                }


//                startActivity(intent);
//                finish();
                break;


        }
    }

    @Override
    public void move() {

        Intent intent = new Intent(Step6Activity.this, Step7Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("dogInfo", mDogInfo);
//

        intent.putExtra("userInfo", mUserInfo);
        startActivity(intent);
        finish();
    }
}