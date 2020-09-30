package com.makeus.dogdog.src.HomeDogDog.startWalking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

public class startWalking extends BaseActivity implements View.OnClickListener {

    SeekBar seekBar;
    DonutView mDonutView;
    Chronometer mWalkingTime, mWalkingDistance;
    ImageView mStartWalking, mStopButton;
    private double mTimetickin = 0;
    private int mPercent;
    private boolean mRunning;
    private long timeWhenStopped = 0;

// 넘어는 오는데 초기 시간 표시 , 처음 화면 바꿔야함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_walking);
        mStartWalking = findViewById(R.id.start_walking);
        mWalkingTime = findViewById(R.id.walking_time_startwalking);
        mWalkingDistance = findViewById(R.id.walking_distance_startwalking);
        mDonutView = findViewById(R.id.donut);
        mStopButton = findViewById(R.id.stopbutton_startwalking);
        mTimetickin = Double.parseDouble(getIntent().getStringExtra("time"));
        mPercent = getIntent().getIntExtra("percent", 0);
        mDonutView.setValue(mTimetickin, mPercent);

        mWalkingTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                chronometer.setText(t);


                // 0.5초 단위로 계속 그릴려면?
                // time % 500 < 100 && time >= 500
                if (time % 18000 < 1000 && time >= 18000) //18초에 1초씩 증가 .. 18초 이상일 때 부터 1%증가
                {
                    mTimetickin += ((double) 1 / (double) 18);
                    System.out.println(mTimetickin);
                    mPercent++;
                    mDonutView.setValue(mTimetickin, mPercent);
                } else if (time % 1000 < 1000 && time >= 1000) {

                    mTimetickin += ((double) 1 / (double) 18);
                    mDonutView.setValue(mTimetickin, mPercent);
//                    mDonutView.setValue(Double.parseDouble(getIntent().getStringExtra("time")), getIntent().getIntExtra("percent", 0));
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
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.setText(time + "");
            }
        });

        mWalkingTime.setBase(SystemClock.elapsedRealtime());
        mWalkingTime.setText("00:00:00");

        mStartWalking.setOnClickListener(this);
        mStopButton.setOnClickListener(this);


    }




    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.start_walking:


                if (!mRunning) { //시작

                    mWalkingTime.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    mWalkingTime.start();
                    mWalkingDistance.setBase(SystemClock.elapsedRealtime());
                    mWalkingDistance.start();
                    mStartWalking.setImageResource(R.drawable.pause_pause);
                    mRunning = true;
                } else { //일시 중지

                    timeWhenStopped = mWalkingTime.getBase() - SystemClock.elapsedRealtime();
                    mWalkingTime.stop();
                    mStartWalking.setImageResource(R.drawable.start);
                    mRunning = false;


                }

                break;
            case R.id.stopbutton_startwalking:


                SharedPreferences prefs = getSharedPreferences("startwalking", MODE_PRIVATE);

                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("time", String.valueOf(mTimetickin));
                editor.putInt("percent", mPercent);

                editor.commit();
                finish();
                break;


        }
    }

}