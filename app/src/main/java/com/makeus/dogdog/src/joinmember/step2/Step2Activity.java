package com.makeus.dogdog.src.joinmember.step2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.joinmember.step2.interfaces.ShowToastStep2;
import com.makeus.dogdog.src.joinmember.step2.models.DuplicateUserIdResponse;
import com.makeus.dogdog.src.joinmember.step3.Step3Activity;

public class Step2Activity extends BaseActivity implements View.OnClickListener, ShowToastStep2 {

    TextView mJoinMessage, mNextButton,mBackButton;
    DuplicateUserIdService mDuplicateUserIdService;

    EditText mInputIdEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        mNextButton=findViewById(R.id.next_button_step);
        mBackButton=findViewById(R.id.backButton_step);
        mNextButton.setOnClickListener(this);
        mInputIdEdt=findViewById(R.id.edit_Input_Text_joinmember);
        mBackButton.setOnClickListener(this);

           }


    @Override
    protected void onResume() {
        super.onResume();

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

                mDuplicateUserIdService =new DuplicateUserIdService(this,mInputIdEdt.getText().toString());
                mDuplicateUserIdService.checkDuplicatedId();


                break;



        }

    }

    @Override
    public void checkDuplicatId(DuplicateUserIdResponse duplicateUserIdResponse) {

        if(duplicateUserIdResponse.getCode()==200)
        {

            Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


            startActivity(intent);
        }else{

            Toast.makeText(getApplicationContext(),"중복되는 아이디입니다. ",Toast.LENGTH_SHORT).show();

        }
    }
}