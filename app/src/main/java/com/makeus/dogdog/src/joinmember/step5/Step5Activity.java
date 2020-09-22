package com.makeus.dogdog.src.joinmember.step5;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step6.Step6Activity;

public class Step5Activity extends BaseActivity implements View.OnClickListener {
    TextView mTellUsAge, mBackButton;
    TextView mJoinMessage, mNextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step5);
        mTellUsAge = findViewById(R.id.tellUsAge_Step2Activity);
        mTellUsAge.setText(Html.fromHtml("<b>" + "반려견의 성별과 나이" + "</b>" + "를" + "<br>" + "</br>" + "알려주세요."));

        mBackButton = findViewById(R.id.backButton_step);
        mBackButton.setOnClickListener(this);
        mNextButton=findViewById(R.id.next_button_step);
        mNextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton_step:
                finish();
                overridePendingTransition(0,0); // finish()시 애니메이션 삭제

                break;
            case R.id.next_button_step:
                Intent intent = new Intent(Step5Activity.this, Step6Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                startActivity(intent);

                break;
        }
    }
}