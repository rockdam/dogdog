package com.makeus.dogdog.src.HomeDogDog.homeFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.HomeActivity;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.DogInfo;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.HomeRefreshResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.Result;
import com.makeus.dogdog.src.HomeDogDog.startWalking.startWalking;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements View.OnClickListener, HomeRefreshView {



    TextView mWelcomeMessage,mDogNickName,mDogInfo;
    ImageView mChangeDogs;

    //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressBar mProgressbar;
    int mPercent = 0;
    double mTimeTickin;

    int mTime;

    int mDogIdx;
    TextView mPercentHome;
    ProgressBar mAimProgressBar;
    SharedPreferences mPrefs;
    HomeRefreshService mHomeRefreshService;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

        mPrefs = this.getActivity().getSharedPreferences("startwalking", Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mAimProgressBar = v.findViewById(R.id.progressbar_home);

        mPercentHome = v.findViewById(R.id.percent_home);
        mWelcomeMessage=v.findViewById(R.id.welcomeMessage_main);
        mDogInfo=v.findViewById(R.id.dogInfo_home);
        mDogNickName=v.findViewById(R.id.dogName_home);
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.

        mChangeDogs=v.findViewById(R.id.changeDogs_home);
        mProgressbar=v.findViewById(R.id.progressbar_home);
        mHomeRefreshService=new HomeRefreshService(this);


        TextView startWalking = v.findViewById(R.id.next_button_step);

        mAimProgressBar.setMax(1000);
        startWalking.setOnClickListener(this);
        mChangeDogs.setOnClickListener(this);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mHomeRefreshService.refreshHomeView();
//        mPrefs.edit().clear().commit() ;
//        얘를 자정 지나면 발동 되도록 .;
//        mPercent = mPrefs.getInt("percent", 0);
//        mTimeTickin = Double.parseDouble(mPrefs.getString("timetickin", "0"));
//        System.out.println("이건 어떻게 찍히나"+(int) (mTimeTickin * (double) 10));
//        // 몇 초까지는 안 보이니깐 .. 눈 속임으로 대체 합시다 !... 몇 초까진지 확인 후 조건문 걸기!
//        mAimProgressBar.setProgress((int) (mTimeTickin * (double) 10));
//        https://stackoverflow.com/questions/18192454/progress-bar-pass-float-argument/2133259
//        max값을 올려서 생각하면 됌 .


        System.out.println("여기호출");
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.next_button_step:
                Intent intent = new Intent(getActivity(), startWalking.class);


//                if (mPercent != 0) {
//
//                    intent.putExtra("percent", mPercent);
//                }
//                if (mTimeTickin != 0) {
//                    intent.putExtra("timeTicking", String.valueOf(mTimeTickin));
//
//                }
//                if (mTime != 0) {
//                    intent.putExtra("time", mTime);
//
//                }


                intent.putExtra("dogIdx",mDogIdx);

                startActivity(intent);


                break;


        }

    }

    @Override
    public void refresh(Result result) {


        String nickname =result.getNickName();
        String formattedNickname =getString(R.string.welcome_message,nickname);
        String dogInfo;
        if(result.getDogInfo().getGender().equals("남아"))
        {
            dogInfo = result.getDogInfo().getAge()+ "살 /" +"남아"+"/"+result.getDogInfo().getBreed();

        }else{
            dogInfo = result.getDogInfo().getAge()+ "살 /" +"여아"+"/"+result.getDogInfo().getBreed();

        }
        mPercent=result.getDogInfo().getAcheivedGoal();
        mWelcomeMessage.setText(formattedNickname);
        mDogNickName.setText(result.getDogInfo().getDogName());
        mDogInfo.setText(dogInfo);

        mTime= result.getDogInfo().getTodayTime();
        if(mTime!=0)
        mTimeTickin = ((double) mTime / (18));
        else{
            mTimeTickin=0;

        }
        mAimProgressBar.setProgress((int) (mTimeTickin * (double) 10));
        mPercentHome.setText(String.valueOf(mPercent));
        mDogIdx=result.getDogInfo().getDogIdx();

    }
}