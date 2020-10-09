package com.makeus.dogdog.src.joinmember.step7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.HomeActivity;
import com.makeus.dogdog.src.joinmember.step6.Step6Activity;
import com.makeus.dogdog.src.joinmember.step6.models.DogInfo;
import com.makeus.dogdog.src.joinmember.step6.models.UserInfo;

public class Step7Activity extends BaseActivity implements View.OnClickListener {
    TextView mJoinMessage, mNextButton,mBackButton;
    UserInfo mUserInfo;
    DogInfo mDogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step7);
        mNextButton=findViewById(R.id.mainButton_step);
        mNextButton.setOnClickListener(this);
        mUserInfo=new UserInfo();
        mDogInfo=new DogInfo();
        Intent intent = getIntent();
        if (intent.hasExtra("userInfo")) {

            mUserInfo = (UserInfo) intent.getSerializableExtra("userInfo");

        }
        if (intent.hasExtra("dogInfo")) {

            mDogInfo = (DogInfo) intent.getSerializableExtra("dogInfo");

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mainButton_step:
                Intent intent = new Intent(Step7Activity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                startActivity(intent);
                finish();
                break;

        }
    }
}