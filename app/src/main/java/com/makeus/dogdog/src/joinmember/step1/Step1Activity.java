package com.makeus.dogdog.src.joinmember.step1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step2.Step2Activity;

public class Step1Activity extends BaseActivity implements View.OnClickListener {


    TextView mJoinMessage, mNextTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);
        mJoinMessage = findViewById(R.id.tellUsAge_Step2Activity);

        mNextTxt = findViewById(R.id.next_button_step);
        mJoinMessage.setText(Html.fromHtml("<b>" + "만나서 반가워요!" + "<br>" + "반려견의 이름" + "</br>" + "</b>" + "이 궁금해요."));
        Typeface typeface = ResourcesCompat.getFont(this, R.font.spoqahansansbold);

      //  mJoinMessage.setTypeface(typeface);
        //깃 연동 기념
        //슬랙 연동 기념

        mNextTxt.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.next_button_step:
                Intent intent = new Intent(Step1Activity.this, Step2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                startActivity(intent);

                break;
        }

    }
}