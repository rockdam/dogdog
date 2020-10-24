package com.makeus.dogdog.src;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.makeus.dogdog.R;

public class LodingDialogFragment extends DialogFragment {
//    이유
    public static LodingDialogFragment newInstance() {
        LodingDialogFragment lodingDialogFragment = new LodingDialogFragment();

        return lodingDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        getDialog().setCancelable(false);


        return inflater.inflate(R.layout.loading_fragment,container,false);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {


        FragmentTransaction fm =manager.beginTransaction();

        fm.add(this,tag);
        fm.commitAllowingStateLoss();

    }






}

