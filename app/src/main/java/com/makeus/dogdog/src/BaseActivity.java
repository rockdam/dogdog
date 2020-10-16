package com.makeus.dogdog.src;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.makeus.dogdog.R;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity { // 얘가 근본이 되서 한다.
    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
//        getSupportActionBar().hide(); //hide the title bar
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setStatusBarColor(Color.BLACK);
//        }
//        //이거 말고도 초기 실행될 때 상단바 색 바꿔야함

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);




    }



    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog(); // 스톱 되면 하이드
    }


}
