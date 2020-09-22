package com.makeus.dogdog.src.joinmember.step2;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

public class Step2Activity extends BaseActivity implements View.OnClickListener {

    TextView mTellUsAge,mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5);
        mTellUsAge=findViewById(R.id.tellUsAge_Step2Activity);
        mTellUsAge.setText(Html.fromHtml("<b>"+"반려견의 성별과 나이"+"</b>"+"를" + "<br>"+"</br>"+"알려주세요.") );

        mBackButton=findViewById(R.id.backButton_step2);
        mBackButton.setOnClickListener(this);
           }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.backButton_step2:
                finish();
                overridePendingTransition(0,0); // finish()시 애니메이션 삭제

                break;


        }

    }
}