package com.makeus.dogdog.src.HomeDogDog.startWalking;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

public class startWalking extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    SeekBar seekBar;
    DonutView mDonutView;
    Chronometer mWalkingTime;
    ImageView mStartWalking;
    private boolean running;
    private long timeWhenStopped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_walking);
        mStartWalking =findViewById(R.id.start_walking);
        mWalkingTime = findViewById(R.id.walking_time_startwalking);

        seekBar = findViewById(R.id.seekBar);
        mDonutView = findViewById(R.id.donut);
        seekBar.setOnSeekBarChangeListener(this);


        mWalkingTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                chronometer.setText(t);
            }
        });

        mWalkingTime.setBase(SystemClock.elapsedRealtime());
        mWalkingTime.setText("00:00:00");


        mStartWalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!running) {

                    mWalkingTime.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    mWalkingTime.start();
                    mStartWalking.setImageResource(R.drawable.pause_pause);
                    running =true;
                }else{

                    timeWhenStopped = mWalkingTime.getBase() - SystemClock.elapsedRealtime();
                    mWalkingTime.stop();
                    mStartWalking.setImageResource(R.drawable.start);
                    running =false;

                }
            }
        });
    }


    public void startWalkingTime() {
        if (!running) {

            mWalkingTime.start();
            running = true;
        }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        mDonutView.setValue(progress);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}