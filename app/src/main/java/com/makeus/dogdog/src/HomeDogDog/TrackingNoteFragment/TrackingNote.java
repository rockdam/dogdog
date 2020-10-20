package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.AddTrackingNote;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.interfaces.TrackingNoteView;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingMonthResult;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models.WalkingdayResult;
import com.makeus.dogdog.src.collapsiblecalendarview.data.CalendarAdapter;
import com.makeus.dogdog.src.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackingNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackingNote extends Fragment implements TrackingNoteView {


    ConstraintLayout constraintLayoutIncludeLayout,blankNoteContraintlayout;
    boolean showContraintlayout;
    CollapsibleCalendar collapsibleCalendar;
    CalendarAdapter calendarAdapter;
    TrackingNoteService mTrackingNoteService;
    TextView mCompleteTime,mCompleteDistance,mCompleteMission,mToday;
    int mYear, mMonth, mDay;
    /**
     * 일 저장 할 리스트
     */
    private ArrayList<String> dayList;
    private Calendar mCal;
    /**
     * 캘린더 변수
     */
    /**
     * 그리드뷰 어댑터
     */

    ImageView addTrackingNote;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrackingNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackingNote.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackingNote newInstance(String param1, String param2) {
        TrackingNote fragment = new TrackingNote();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    //https://heum-story.tistory.com/6 커스텀뷰 만드는중
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tracking_note, container, false);
        addTrackingNote = v.findViewById(R.id.addNote_AddTrackingNote);
        showContraintlayout = true;

        blankNoteContraintlayout=v.findViewById(R.id.blanknote_TrackingNote);


        showContraintlayout = false;
        addTrackingNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTrackingNote.class);
                startActivity(intent);
            }
        });
        collapsibleCalendar = v.findViewById(R.id.calendar_trackingnote);





        constraintLayoutIncludeLayout = v.findViewById(R.id.writenote_include);
        constraintLayoutIncludeLayout.setVisibility(View.GONE);
        mCompleteTime=constraintLayoutIncludeLayout.findViewById(R.id.completetime_include);
        mCompleteDistance=constraintLayoutIncludeLayout.findViewById(R.id.completedistance_include);
        mCompleteMission=constraintLayoutIncludeLayout.findViewById(R.id.completeMission_include);
        mToday=constraintLayoutIncludeLayout.findViewById(R.id.today_include);
        //Include 레이아웃 사용하는 법 .


        mTrackingNoteService = new TrackingNoteService(this, initialQueryStringDate());
        mTrackingNoteService.refreshUpdateWalkingMonth();
        //월은 9가 10월
        // Inflate the layout for this fragment

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() { //일별 조회 api


                mDay=collapsibleCalendar.getSelectedDay().getDay();
                mTrackingNoteService = new TrackingNoteService(TrackingNote.this, createQueryStringDayDate(mYear, mMonth,mDay));
                mTrackingNoteService.refreshUpdateWalkingDay();

//                Toast.makeText(getContext(),""+collapsibleCalendar.getSelectedItemPosition(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "" + collapsibleCalendar.getSelectedDay().getDay(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemClick(View v) { //여기서는 클릭하면 화면 .


            }

            @Override
            public void onDataUpdate() {

            }



            //나중에 어댑터 이용해서 받도록 코드 고치기 .
//            collapsibleCalendar.getSelectedDay().getYear()


            @Override
            public void onMonthChange(int year, int month) { //이거 눌를 때 마다 호출 하고 에드 event for문
                Toast.makeText(getContext(), "년도" + year + "monthChange" + month, Toast.LENGTH_SHORT).show();
                mYear = year;
                mMonth = month;
                mTrackingNoteService = new TrackingNoteService(TrackingNote.this, createQueryStringMonthDate(mYear, mMonth));
                mTrackingNoteService.refreshUpdateWalkingMonth();
            }

            @Override
            public void onWeekChange(int position) {

            }
        });
        return v;
    }

    String initialQueryStringDate() {
        Calendar todayCal = new GregorianCalendar(TimeZone.getTimeZone("GMT+9"));
        String month;

        int checkMonth = todayCal.get(Calendar.MONTH);
        if (checkMonth < 9) {
            month = "0" + String.valueOf(todayCal.get(Calendar.MONTH) + 1);
        } else {

            month = String.valueOf(todayCal.get(Calendar.MONTH) + 1);


        }
        String date = todayCal.get(Calendar.YEAR) + "-" + month;

        mYear = todayCal.get(Calendar.YEAR);
        mMonth = todayCal.get(Calendar.MONTH) + 1;
        mDay = todayCal.get(Calendar.DAY_OF_MONTH);
        return date;

    }

    String createQueryStringMonthDate(int year, int month) {

        String Stringmonth;

        int checkMonth = month;
        if (checkMonth <= 9) {
            Stringmonth = "0" + String.valueOf(month);
        } else {

            Stringmonth = String.valueOf(month);


        }
        String date = year + "-" + Stringmonth;


        return date;
    }
    String createQueryStringDayDate(int year, int month,int day) {

        String Stringmonth;

        String Stringday;
        int checkMonth = month;
        if (checkMonth <= 9) {
            Stringmonth = "0" + String.valueOf(month);
        } else {

            Stringmonth = String.valueOf(month);


        }

        int checkDay =day;
        if(checkDay <=9)
        {
            Stringday = "0" + String.valueOf(day);
        }else{


            Stringday = String.valueOf(day);

        }
        String date = year + "-" + Stringmonth+"-"+Stringday ;


        return date;
    }

      @Override
    public void updateMonth(WalkingMonthResult walkingMonthResult) {


//        if(showContraintlayout)
//        {
//            constraintLayoutIncludeLayout.setVisibility(View.GONE);
//            showContraintlayout=false;
//        }else{
//
//            constraintLayoutIncludeLayout.setVisibility(View.VISIBLE);
//            showContraintlayout=true;
//        }
        if (walkingMonthResult != null) {
            for (int i = 0; i < walkingMonthResult.getDays().size(); i++) {

                Log.e("날짜 확인", "" + walkingMonthResult.getDays().get(i));

                if (mDay == walkingMonthResult.getDays().get(i)) {//오늘 날짜는?
                    constraintLayoutIncludeLayout.setVisibility(View.VISIBLE);
                    showContraintlayout = true; // 오늘이면 보여야지 ./ 여기서 오늘 날짜 Api를 엮어야 되네 ;?
                    blankNoteContraintlayout.setVisibility(View.INVISIBLE);
                  String date=  createQueryStringDayDate(mYear,mMonth,walkingMonthResult.getDays().get(i));
                    mTrackingNoteService = new TrackingNoteService(TrackingNote.this,date);
                    mTrackingNoteService.refreshUpdateWalkingDay();//날짜 엮기


                }
                collapsibleCalendar.addEventTag(mYear, mMonth, walkingMonthResult.getDays().get(i));


            }

        }
    }

    @Override
    public void initialTackingNot(WalkingMonthResult walkingMonthResult) {

    }

    @Override
    public void updateDay(WalkingdayResult walkingdayResult) {


        if(walkingdayResult!=null) {

            constraintLayoutIncludeLayout.setVisibility(View.VISIBLE);
            blankNoteContraintlayout.setVisibility(View.INVISIBLE);
            mCompleteMission.setText(walkingdayResult.getPercent() + "%");
            mCompleteDistance.setText("" + walkingdayResult.getWalkingDistance());
            mCompleteTime.setText("" + walkingdayResult.getWalkingTime());
            mToday.setText(walkingdayResult.getDate());

        }else{
            constraintLayoutIncludeLayout.setVisibility(View.INVISIBLE);
            blankNoteContraintlayout.setVisibility(View.VISIBLE);
        }//값이 없으면 없다고 나와야지

    }
}