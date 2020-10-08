package com.makeus.dogdog.src.joinmember.step6;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step5.Step5Activity;
import com.makeus.dogdog.src.joinmember.step7.Step7Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step6Activity extends BaseActivity implements View.OnClickListener {
    TextView mJoinMessage, mNextButton,mBackButton;
    EditText mEdit_Input_Text_joinmember;
    String mKg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step6);
        mNextButton=findViewById(R.id.next_button_step);
        mBackButton=findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);


        mEdit_Input_Text_joinmember=findViewById(R.id.edit_Input_Text_joinmember);

    }

    public static boolean isValidKg(final String Password) {

        Pattern pattern;
        Matcher matcher;

        final String KG_PATTERN = "^(0|[1-9]\\d*)(\\.\\d+)?$";
        pattern = Pattern.compile(KG_PATTERN);
        matcher = pattern.matcher(Password);

        return matcher.matches();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mEdit_Input_Text_joinmember.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mKg =mEdit_Input_Text_joinmember.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(isValidKg(editable.toString()))
                {

                    mKg+="kg";

                    mEdit_Input_Text_joinmember.setText(mKg);
                    mEdit_Input_Text_joinmember.setSelection(mEdit_Input_Text_joinmember.getText().length()-2);
                }


            }
        });
//        https://stackoverflow.com/questions/2811031/decimal-or-numeric-values-in-regular-expression-validation 출처
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.backButton_step:
                Intent back = new Intent(Step6Activity.this, Step5Activity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);



                overridePendingTransition(0,0); // finish()시 애니메이션 삭제
                startActivity(back);
                finish();
                break;
            case R.id.next_button_step:
                Intent intent = new Intent(Step6Activity.this, Step7Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                startActivity(intent);
                finish();
                break;



        }
    }
}