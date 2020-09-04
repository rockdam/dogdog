package com.makeus.dogdog.src.joinmember.step2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

public class Step2 extends BaseActivity {

    TextView mTellUsAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        mTellUsAge=findViewById(R.id.tellUsAge_Step2Activity);
        mTellUsAge.setText(Html.fromHtml("<b>"+"반려견의 성별과 나이"+"</b>"+"를" + "<br>"+"</br>"+"알려주세요.") );

    }
}