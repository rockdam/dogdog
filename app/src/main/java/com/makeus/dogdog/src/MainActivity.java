package com.makeus.dogdog.src;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.makeus.dogdog.R;

/**
 * Created by kitte on 2016-12-29.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button monthBtn = (Button)findViewById(R.id.calendar_month_btn);
        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaTCalendarActivity.class);
                startActivity(intent);
            }
        });

        Button weekBtn = (Button)findViewById(R.id.calendar_week_btn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaTCalendarWeekActivity.class);
                startActivity(intent);
            }
        });
    }
}
