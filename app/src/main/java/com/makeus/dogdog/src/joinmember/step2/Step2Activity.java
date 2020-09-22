package com.makeus.dogdog.src.joinmember.step2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step3.Step3Activity;

public class Step2Activity extends BaseActivity implements View.OnClickListener {

    TextView mJoinMessage, mNextButton,mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);

        mNextButton=findViewById(R.id.next_button_step);
        mBackButton=findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

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
                Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                startActivity(intent);
                break;



        }

    }
}