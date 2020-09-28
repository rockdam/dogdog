package com.makeus.dogdog.src.calendar;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

/**
 * Created by kitte on 2016-12-29.
 */
//http://kittenjunkwon.blogspot.com/2016/12/android_29.html 주단위 달력
//    저도 정확히는 모르겠지만 드래그앤 드랍은 아닌 것 같습니다. 리스너 중에GestureDetector.SimpleOnGestureListener 라는게 있는데 이걸 활용하면 되지 않을까 싶어요. 아래로 스와이프하면 주간달력이 서서히 사라지면서 월간달력이 서서히 위에서 아래로 나오게? 아니면 찾아보시면 라이브러리가 많을 수도 있습니다 .
//
//출처: https://altongmon.tistory.com/818 [IOS를 Java]
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button monthBtn = findViewById(R.id.calendar_month_btn);
        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaTCalendarActivity.class);
                startActivity(intent);
            }
        });

        Button weekBtn = findViewById(R.id.calendar_week_btn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaTCalendarWeekActivity.class);
                startActivity(intent);
            }
        });
    }
}
