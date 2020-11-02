package com.makeus.dogdog.src;

import android.Manifest;
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
import androidx.fragment.app.FragmentManager;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.makeus.dogdog.R;

import java.util.List;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity { // 얘가 근본이 되서 한다.
    public ProgressDialog mProgressDialog;

    LodingDialogFragment lodingDialogFragment;
    FragmentManager manager;
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



        lodingDialogFragment=LodingDialogFragment.newInstance();
        manager=this.getSupportFragmentManager() ;
        //로딩 다이얼로그


    }
    public void showDogDogLoadingDialog()
    {
        if(!lodingDialogFragment.isAdded())
        lodingDialogFragment.show(manager,"loader");
    }
    public void hideDogDogLoadingDialog()
    {
        if(lodingDialogFragment.isAdded())
        {

            lodingDialogFragment.dismissAllowingStateLoss();
        }

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
             hideDogDogLoadingDialog();
    }

    private void showPermissionDialog() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getBaseContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getBaseContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

}
