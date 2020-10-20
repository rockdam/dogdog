package com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.UpdateUserImage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.makeus.dogdog.R;

public class UpdateUserImage extends AppCompatActivity {

    ImageView defaultImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_image);
        defaultImage=findViewById(R.id.default_image_updateuserimage);

        defaultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}