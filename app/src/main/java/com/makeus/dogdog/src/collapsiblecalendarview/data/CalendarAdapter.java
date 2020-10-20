package com.makeus.dogdog.src.collapsiblecalendarview.data;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.makeus.dogdog.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by shrikanthravi on 06/03/18.
 */

public class CalendarAdapter {
    private int mFirstDayOfWeek = 0;
    private Calendar mCal;
    private LayoutInflater mInflater;
    private Context mContext;

    List<Day> mItemList = new ArrayList<>();
    List<View> mViewList = new ArrayList<>();
    List<Event> mEventList = new ArrayList<>();

    public CalendarAdapter(Context context, Calendar cal) {
        this.mCal = (Calendar) cal.clone();
        this.mCal.set(Calendar.DAY_OF_MONTH, 1);
        mContext=context;
        mInflater = LayoutInflater.from(context);

        refresh();
    }

    // public methods
    public int getCount() {
        return mItemList.size();
    }

    public Day getItem(int position) {
        return mItemList.get(position);
    }

    public View getView(final int position) {
        return mViewList.get(position);
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        mFirstDayOfWeek = firstDayOfWeek;
    }

    public Calendar getCalendar() {
        return mCal;
    }

    public void addEvent(Event event) {
        mEventList.add(event);
    }
    public void notifyWritedday( ArrayList<Integer> mDaylist)
    {

    }

    public void refresh() {
        // clear data
        mItemList.clear();
        mViewList.clear();

        // set calendar
        int year = mCal.get(Calendar.YEAR);
        int month = mCal.get(Calendar.MONTH);

        mCal.set(year, month, 1);

        int lastDayOfMonth = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfWeek = mCal.get(Calendar.DAY_OF_WEEK) - 1;

        // generate day list
        int offset = 0 - (firstDayOfWeek - mFirstDayOfWeek) + 1;
        int length = (int) Math.ceil((float) (lastDayOfMonth - offset + 1) / 7) * 7;


        for (int i = offset; i < length + offset; i++) {
            int numYear;
            int numMonth;
            int numDay;

            Calendar tempCal = Calendar.getInstance();
            if (i <= 0) { // prev month
                if (month == 0) {
                    numYear = year - 1;
                    numMonth = 11;
                } else {
                    numYear = year;
                    numMonth = month - 1;
                }
                tempCal.set(numYear, numMonth, 1);
                numDay = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH) + i;
            } else if (i > lastDayOfMonth) { // next month
                if (month == 11) {
                    numYear = year + 1;
                    numMonth = 0;
                } else {
                    numYear = year;
                    numMonth = month + 1;
                }
                tempCal.set(numYear, numMonth, 1);
                numDay = i - lastDayOfMonth;
            } else {
                numYear = year;
                numMonth = month;
                numDay = i;
            }

            Day day = new Day(numYear, numMonth, numDay);

            View view = mInflater.inflate(R.layout.day_layout, null);
            TextView txtDay = (TextView) view.findViewById(R.id.txt_day);
            ImageView imgEventTag = (ImageView) view.findViewById(R.id.img_event_tag);

            Typeface typeface = ResourcesCompat.getFont(mContext, R.font.spoqahansansbold);


            txtDay.setText(String.valueOf(day.getDay()));
            txtDay.setTypeface(typeface);
            txtDay.setTextSize(16);
            //여기가 달력 날짜 글씨 크기 조정 .
//            if(day.getDay() ==1)
//            {
//                txtDay.setAlpha(0.2f);
//            }
            if (day.getMonth() != mCal.get(Calendar.MONTH)) { //이번달이 아니면
                txtDay.setAlpha(0.2f);





            }


            // 여기다 추가로 글이 등록된 날짜는 파랗게 칠하면 될 듯


            for (int j = 0; j < mEventList.size(); j++) {
                Event event = mEventList.get(j);
                if (day.getYear() == event.getYear()
                        && day.getMonth() == event.getMonth()
                        && day.getDay() == event.getDay()) {
                    imgEventTag.setVisibility(View.VISIBLE);
                }
            }

            mItemList.add(day);
            mViewList.add(view);
        }
    }
}
