package com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.UpdateUserImage.UpdateUserImage;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.interfaces.UpdateUserView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.models.Result;

import java.util.ArrayList;

public class UpdateUserProfile extends BaseActivity implements UpdateUserView {


    UpdateUserAdapter userAdapter;
    UpdateUserService updateUserService;
    RecyclerView updateUserRecyclerView;
    ArrayList<Result> resultsArraylist;
    FrameLayout frameLayoutUpdateuserprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);
        updateUserRecyclerView=findViewById(R.id.recyclerview_updateuserProfile);
        resultsArraylist=new ArrayList<>();
        userAdapter=new UpdateUserAdapter(this,resultsArraylist);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this);
        updateUserRecyclerView.setLayoutManager(layoutManager);
        updateUserRecyclerView.setAdapter(userAdapter);

        frameLayoutUpdateuserprofile=findViewById(R.id.frameLayout_updateuserprofile);

        frameLayoutUpdateuserprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(UpdateUserProfile.this, UpdateUserImage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void refresh(Result result) {



    }
}