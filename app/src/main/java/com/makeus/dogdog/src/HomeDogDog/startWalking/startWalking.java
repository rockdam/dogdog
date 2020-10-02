package com.makeus.dogdog.src.HomeDogDog.startWalking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
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
    ImageView mStartWalking, mStopButton, mStartCamera;
    private double mTimetickin = 0;
    private int mPercent;
    private boolean mRunning;
    private long timeWhenStopped = 0;
    private long mStartTime;
    long time;
    static final int REQUEST_IMAGE_CAPTURE = 1;
// 넘어는 오는데 초기 시간 표시 , 처음 화면 바꿔야함


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_walking);
        mStartWalking = findViewById(R.id.start_walking);
        mWalkingTime = findViewById(R.id.walking_time_startwalking);
        mWalkingDistance = findViewById(R.id.walking_distance_startwalking);
        mDonutView = findViewById(R.id.donut);
        mStopButton = findViewById(R.id.stopbutton_startwalking);
        mStartCamera = findViewById(R.id.cameraApp_startWalking);
        if(getIntent().getStringExtra("timeTickin")!=null) {
            mTimetickin = Double.parseDouble(getIntent().getStringExtra("timeTickin"));
        }
        mPercent = getIntent().getIntExtra("percent", 0);
        mStartTime = getIntent().getLongExtra("time", 0);
        mDonutView.setValue(mTimetickin, mPercent);


        mStartCamera.setOnClickListener(this);
        mWalkingTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {


                time = mStartTime + SystemClock.elapsedRealtime() - chronometer.getBase();

//                System.out.println("time : " + time);

                chronometer.setText(calculate(time));

                //1000

                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000) / 1000;




                mTimetickin = ((double) s / (18));
                mPercent=s/18;
                mDonutView.setValue(mTimetickin, mPercent);
//                    System.out.println("time : " + time);
                System.out.println("mTimetickin" + mTimetickin);
                System.out.println("Time체크" + s);

//                    mDonutView.setValue(Double.parseDouble(getIntent().getStringExtra("time")), getIntent().getIntExtra("percent", 0));


            }
        });


        mWalkingDistance.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.setText(mStartTime + "");
            }
        });

        mWalkingTime.setBase(SystemClock.elapsedRealtime());
        mWalkingTime.setText(calculate(time));

        mStartWalking.setOnClickListener(this);
        mStopButton.setOnClickListener(this);


    }

    public String calculate(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);

        return t;


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
                    mWalkingDistance.stop();
                    mStartWalking.setImageResource(R.drawable.start);
                    mRunning = false;


                }

                break;
            case R.id.stopbutton_startwalking:


                SharedPreferences prefs = getSharedPreferences("startwalking", MODE_PRIVATE);

                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("timetickin", String.valueOf(mTimetickin));
                editor.putInt("percent", mPercent);
                editor.putLong("time", time);

                editor.commit();
                finish();
                break;
            case R.id.cameraApp_startWalking:

                dispatchTakePictureIntent();
                break;


        }
    }

}