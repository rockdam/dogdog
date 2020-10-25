package com.makeus.dogdog.src.HomeDogDog.MypageFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.MypageFragment.interfaces.MypageView;
import com.makeus.dogdog.src.HomeDogDog.MypageFragment.models.MyPageRanking;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mypage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mypage extends Fragment implements MypageView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView dogImage;
    TextView dogInfo, dogName, dogGoal, dogwalkingcnt, myranking;

    public Mypage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mypage.
     */
    // TODO: Rename and change types and number of parameters
    public static Mypage newInstance(String param1, String param2) {
        Mypage fragment = new Mypage();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mypage, container, false);
        dogImage = v.findViewById(R.id.default_dogImage_home);
        dogName = v.findViewById(R.id.dogName_home);
        dogInfo = v.findViewById(R.id.dogInfo_home);
        dogGoal = v.findViewById(R.id.goal_mypage);
        dogwalkingcnt = v.findViewById(R.id.walkingcnt_mypage);
        myranking = v.findViewById(R.id.myranking_mypage);
        MyPageService myPageService =new MyPageService(this);
        myPageService.refreshMypage();


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void refresh(MyPageRanking myPageRanking) {


        Glide.with(this)
                .load(myPageRanking.getDogImg())
                .circleCrop()

                .override(54, 54) // ex) override(600, 200)
                .into(dogImage);
        dogName.setText(myPageRanking.getDogName());

        dogInfo.setText(myPageRanking.getAge() + "살 /" + "남아" + "/" + myPageRanking.getBreed());
        dogwalkingcnt.setText(myPageRanking.getWalkingCnt());

        if(myPageRanking.getMyRanking().equals("-1"))
        {

            myranking.setText("아직 산책한 횟수가 없습니다 :)");

        }else{

            myranking.setText(myPageRanking.getMyRanking()+" 등");

        }


    }
}