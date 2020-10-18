package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.AddTrackingNote;
import com.makeus.dogdog.src.collapsiblecalendarview.data.CalendarAdapter;
import com.makeus.dogdog.src.collapsiblecalendarview.data.Event;
import com.makeus.dogdog.src.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackingNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackingNote extends Fragment {

    GridView mCalendar;

    CollapsibleCalendar collapsibleCalendar;
    CalendarAdapter calendarAdapter;
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
        View v =inflater.inflate(R.layout.fragment_tracking_note, container, false);
        addTrackingNote=v.findViewById(R.id.addNote_AddTrackingNote);

        addTrackingNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), AddTrackingNote.class);
                startActivity(intent);
            }
        });
        collapsibleCalendar=v.findViewById(R.id.calendar_trackingnote);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {


//                Toast.makeText(getContext(),""+collapsibleCalendar.getSelectedItemPosition(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(),""+collapsibleCalendar.getSelectedDay().getDay(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(View v) {


            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {
                Toast.makeText(getContext(),"monthChange",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onWeekChange(int position) {

            }
        });
        collapsibleCalendar.addEventTag(2020,9,10);
        //월은 9가 10월
        // Inflate the layout for this fragment
        return v;
    }


}