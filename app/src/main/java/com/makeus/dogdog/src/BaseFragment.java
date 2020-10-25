package com.makeus.dogdog.src;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.makeus.dogdog.R;

public class BaseFragment extends Fragment {
   public LodingDialogFragment lodingDialogFragment;
    public FragmentManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lodingDialogFragment=LodingDialogFragment.newInstance();
        manager=getActivity().getSupportFragmentManager() ;


    }



    @Override
    public void onStop() {
        super.onStop();
        hideDogDogLoadingDialog();
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
}
