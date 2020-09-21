package com.makeus.dogdog.src.HomeDogDog.startWalking;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

public class startWalking extends BaseActivity  {

    SeekBar seekBar;
    DonutView mDonutView;
    Chronometer mWalkingTime,mWalkingDistance;
    ImageView mStartWalking;
    private int mTimetickin =0;
    private boolean mRunning;
    private long timeWhenStopped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_walking);
        mStartWalking =findViewById(R.id.start_walking);
        mWalkingTime = findViewById(R.id.walking_time_startwalking);
        mWalkingDistance = findViewById(R.id.walking_distance_startwalking);
        mDonutView = findViewById(R.id.donut);


        mWalkingTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                chronometer.setText(t);
                if(time %18000 <1000 && time >=18000) //18초에 1초씩 증가 .. 18초 이상일 때 부터 1%증가
                {
                    mTimetickin++;
                    mDonutView.setValue(mTimetickin);
                }
//                if(true)
//                {
//                    mTimetickin++;
//                    mDonutView.setValue(mTimetickin);
//                }
            }
        });
        mWalkingDistance.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime()- chronometer.getBase();
                chronometer.setText(time+"");
            }
        });

        mWalkingTime.setBase(SystemClock.elapsedRealtime());
        mWalkingTime.setText("00:00:00");


        mStartWalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mRunning) { //시작

                    mWalkingTime.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    mWalkingTime.start();
                    mWalkingDistance.setBase(SystemClock.elapsedRealtime());
                    mWalkingDistance.start();
                    mStartWalking.setImageResource(R.drawable.pause_pause);
                    mRunning =true;
                }else{ //일시 중지

                    timeWhenStopped = mWalkingTime.getBase() - SystemClock.elapsedRealtime();
                    mWalkingTime.stop();
                    mStartWalking.setImageResource(R.drawable.start);
                    mRunning =false;

                }
            }
        });
    }



}