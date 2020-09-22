package com.makeus.dogdog.src.joinmember;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step1.Step1Activity;

public class JoinActivity extends BaseActivity implements View.OnClickListener {


    TextView mJoinmemberButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join);

        mJoinmemberButton=findViewById(R.id.joinmember_acitivity_join);

        mJoinmemberButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch(view.getId())
        {

            case R.id.joinmember_acitivity_join:

                Intent intent =new Intent(JoinActivity.this, Step1Activity.class);
                startActivity(intent);


                break;


        }

    }
}