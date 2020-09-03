package com.makeus.dogdog.src.joinmember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

public class JoinActivity extends BaseActivity implements View.OnClickListener {


    TextView mJoinMessage,mNextTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        mJoinMessage=findViewById(R.id.welcome_message_join);
        mNextTxt=findViewById(R.id.nextTxt_joinActivity);
        mJoinMessage.setText(Html.fromHtml("<b>"+"만나서 반가워요!"+"<br>"+"반려견의 이름"+"</br>"+"</b>"+"이 궁금해요.") );
        //깃 연동 기념
        //슬랙 연동 기념




    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.nextTxt_joinActivity:


                break;
        }

    }
}