package com.makeus.dogdog.src.HomeDogDog.startWalking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.makeus.dogdog.R;

public class DonutView extends View {
    Context context;
    int size;
    int value;
    int strokeSize;
    int textSize;

    int width;
    int height;

    public DonutView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public DonutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public DonutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    public DonutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        init();
    }
    private void init(){
        size=getResources().getDimensionPixelSize(R.dimen.donut_size);
        strokeSize=getResources().getDimensionPixelSize(R.dimen.donut_stroke_size);
        textSize=getResources().getDimensionPixelSize(R.dimen.donut_textSize);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);

    }
    public void setValue(int value)
    {
        this.value=value;
        invalidate(); // 그림 다시 그리기
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.alpha(Color.CYAN));
        RectF recF=new RectF(20,20,size-20,size-20);

        Paint paint =new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeSize);

        canvas.drawArc(recF,0,360,false,paint);

        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(recF,-90,value,false,paint);

        paint.setTextSize(textSize);
        paint.setStrokeWidth(10);

        String txt=String.valueOf(value);
        int xPos=width/2 -(int)(paint.measureText(txt)/2);
        int yPos=(int)(height/2- ((paint.descent()+paint.ascent())/2));

        canvas.drawText(txt,xPos,yPos,paint);



    }
}
