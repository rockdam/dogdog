package com.makeus.dogdog.src.HomeDogDog.startWalking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.makeus.dogdog.R;

public class DonutView extends View {
    Context context;
    int size;
    double value;
    int strokeSize;
    int textSize;

    int percent;
    int width;
    int height;
    int percentSize;

    public DonutView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DonutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DonutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public DonutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        size = getResources().getDimensionPixelSize(R.dimen.donut_size);
        strokeSize = getResources().getDimensionPixelSize(R.dimen.donut_stroke_size);
        textSize = getResources().getDimensionPixelSize(R.dimen.donut_textSize);
        percentSize = getResources().getDimensionPixelSize(R.dimen.donut_percentSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

    }

    public void setValue(double value, int percent) {
        this.value = value;

        this.percent = percent;
        invalidate(); // 그림 다시 그리기
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF recF = new RectF(40, 40, size - 40, size - 40);

        Paint paint = new Paint();
        int colorDonutUnfinished = ContextCompat.getColor(context, R.color.donutUnFinished);
        paint.setColor(colorDonutUnfinished);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeSize);

        canvas.drawArc(recF, 0, 360, false, paint);
        int colorDonutFinished = ContextCompat.getColor(context, R.color.donutFinished);
        int initDonutTextColor = ContextCompat.getColor(context, R.color.initDonutTextColor);


        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(colorDonutFinished);
        canvas.drawArc(recF, -90, (float) (value * 3.6), false, paint);
//        (value * 3.6) 이래야 한바퀴가 맞습니다. value 360 기준이 꽉차는거 .
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL); //이래야 글자에 색깔이 가득 찹니다 .
        paint.setStrokeWidth(20);

        if (percent == 0) {// 초기에 글자 색

            paint.setColor(initDonutTextColor);
        } else {


            paint.setColor(colorDonutFinished);

        }
        String txt = String.valueOf(percent);
        String result = "" + percent;
        String testSizeText = percent + "%";

        int xPos = width / 2 - (int) (paint.measureText(testSizeText) / 2);
        int yPos = (int) (height / 2 - ((paint.descent() + paint.ascent()) / 2));

        Typeface typeface = ResourcesCompat.getFont(context, R.font.spoqahansansbold);
        paint.setTypeface(typeface);

        canvas.drawText(result, xPos, yPos, paint);

        int xNewPos = width / 2 + (int) (paint.measureText(testSizeText) / 2) - percentSize + 10;
        // 퍼센트 글자만큼 앞으로 얘는 숫자보다 뒤로 가기 위해서 덧셈함 ..
        //너무 붙어있어서 10 더함
        paint.setTextSize(percentSize);
        canvas.drawText("%", xNewPos, yPos, paint);

    }
//    그리기는 여기를 참조하였습니다.
//    https://baramziny.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%ED%85%8D%EC%8A%A4%ED%8A%B8-%EA%B7%B8%EB%A6%AC%EA%B8%B0

}
