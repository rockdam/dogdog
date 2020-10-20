package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

public class AddTrackingNote extends BaseActivity {


    ImageView takeaPicture,searchGellary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking_note);

        takeaPicture=findViewById(R.id.takeaPicture_addTrackingNote);
        searchGellary=findViewById(R.id.searchGellary_trackingNote);


    }
}
//button.setOnTouchListener(new OnTouchListener() {
