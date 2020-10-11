package com.makeus.dogdog.src.joinmember.step7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.HomeActivity;
import com.makeus.dogdog.src.joinmember.step6.interfaces.MoveAcitivity7Interface;
import com.makeus.dogdog.src.joinmember.step6.models.dogInfo;
import com.makeus.dogdog.src.joinmember.step6.models.userInfo;

public class Step7Activity extends BaseActivity implements View.OnClickListener, MoveAcitivity7Interface {
    TextView mJoinMessage, mNextButton,mBackButton;
    userInfo mUserInfo;
    dogInfo mDogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step7);
        mNextButton=findViewById(R.id.mainButton_step);
        mNextButton.setOnClickListener(this);
        mUserInfo=new userInfo();
        mDogInfo=new dogInfo();
        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {

            mUserInfo = (userInfo) intent.getSerializableExtra("userInfo");

        }
        if (intent.hasExtra("dogInfo")) {

            mDogInfo = (dogInfo) intent.getSerializableExtra("dogInfo");

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mainButton_step:
                Intent intent = new Intent(Step7Activity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("dogInfo", mDogInfo);
//
                intent.putExtra("userInfo", mUserInfo);

                startActivity(intent);
                finish();
                break;

        }
    }

    @Override
    public void move() {

    }
}