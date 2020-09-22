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

public class Step7Activity extends BaseActivity implements View.OnClickListener {
    TextView mJoinMessage, mNextButton,mBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step7);
        mNextButton=findViewById(R.id.mainButton_step);
        mNextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mainButton_step:
                Intent intent = new Intent(Step7Activity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                startActivity(intent);
                break;

        }
    }
}