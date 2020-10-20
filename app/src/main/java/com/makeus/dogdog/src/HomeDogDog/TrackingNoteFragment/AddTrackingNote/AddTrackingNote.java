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


        searchGellary.setOnTouchListener(new View.OnTouchListener() {
            //
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Button Pressed
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //finger was lifted 여기에 이미지 바꾸면 됌 .
                    searchGellary.setImageResource(R.drawable.ic_album_hover);
                }
                return false;
            }
        });


        takeaPicture.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View view, MotionEvent motionEvent) {



                return false;
            }
        });
        takeaPicture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Button Pressed
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //finger was lifted 여기에 이미지 바꾸면 됌 .
                    takeaPicture.setColorFilter(R.color.Black);
                }
                return false;
            }
        });
    }
}
//button.setOnTouchListener(new OnTouchListener() {
