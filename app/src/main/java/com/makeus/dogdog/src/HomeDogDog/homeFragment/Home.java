package com.makeus.dogdog.src.HomeDogDog.homeFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseFragment;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddChangeDogs;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.SelectedPicture;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.interfaces.HomeRefreshView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.models.Result;
import com.makeus.dogdog.src.HomeDogDog.startWalking.StartWalking;
import com.makeus.dogdog.src.LodingDialogFragment;

import static android.app.Activity.RESULT_OK;
import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends BaseFragment implements View.OnClickListener, HomeRefreshView {


    TextView mWelcomeMessage, mDogNickName, mDogInfo;
    ImageView mChangeDogs;

    //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressBar mProgressbar;
    int mPercent = 0;
    double mTimeTickin;


    TextView explainProgressbar;
    ImageView defaultDogImage;
    int mTime;
    TextView mStartWalking;
    int mDogIdx;
    TextView mPercentHome;
    ProgressBar mAimProgressBar;
    SharedPreferences mPrefs;
    HomeRefreshService mHomeRefreshService;
    ImageView mSmallestCamera;

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
        defaultDogImage = v.findViewById(R.id.default_dogImage_mypage);
        mSmallestCamera = v.findViewById(R.id.smallest_camera_home);
        mPercentHome = v.findViewById(R.id.percent_home);
        mWelcomeMessage = v.findViewById(R.id.welcomeMessage_main);
        mDogInfo = v.findViewById(R.id.dogInfo_home);
        mDogNickName = v.findViewById(R.id.dogName_home);
        explainProgressbar = v.findViewById(R.id.explainProgressbar_home);
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        lodingDialogFragment = LodingDialogFragment.newInstance();
        manager = getActivity().getSupportFragmentManager();
        mChangeDogs = v.findViewById(R.id.changeDogs_home);
        mProgressbar = v.findViewById(R.id.progressbar_home);


        mStartWalking = v.findViewById(R.id.next_button_step);


// 강아지 로딩 파일 완성
        mAimProgressBar.setMax(1000);
        mStartWalking.setOnClickListener(this);
        mChangeDogs.setOnClickListener(this);


        defaultDogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectedPicture.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onPause() {
        super.onPause();

        Log.e("여기 되나요?", "제발요");
    }


    @Override
    public void onResume() {
        super.onResume();


        mHomeRefreshService = new HomeRefreshService(this);
        showDogDogLoadingDialog();
        mHomeRefreshService.refreshHomeView();
        //여기로 해야 기본 선택에서 해제되도 리프뤠시

    }

    // 프래그먼트 생명주기 좀 봐라 여기로 안돌아온다 .
    @Override
    public void onStart() {
        super.onStart();
        Log.e("여기 되나요?ㅇ", "제발요");

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
                Intent intent = new Intent(getActivity(), StartWalking.class);


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


                intent.putExtra("dogIdx", mDogIdx);

                startActivity(intent);

                getActivity().finish();
                break;

            case R.id.changeDogs_home:
                Intent changeDogs = new Intent(getActivity(), AddChangeDogs.class);

                changeDogs.putExtra("dogIdx", mDogIdx);

                startActivityForResult(changeDogs, 0);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case 0:
                    String url = data.getStringExtra("Url");

                    mHomeRefreshService.refreshHomeView();
                    // MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                    Log.e("Url", "" + url);

                    break;
            }

        }

    }

    @Override
    public void refresh(Result result) {


        String nickname = result.getNickName();
        String formattedNickname = getString(R.string.welcome_message, nickname);
        String dogInfo;
        //이 코드가 그동안 어떻게 돌아간거지??? 남아라고 써있었는데 분명 .
        if (result.getDogInfo().getGender().equals("male")) {
            dogInfo = result.getDogInfo().getAge() + "살 /" + "남아" + "/" + result.getDogInfo().getBreed();

        } else {
            dogInfo = result.getDogInfo().getAge() + "살 /" + "여아" + "/" + result.getDogInfo().getBreed();

        }
        mPercent = result.getDogInfo().getAcheivedGoal();
        mWelcomeMessage.setText(formattedNickname);


        mDogNickName.setText(result.getDogInfo().getDogName());
        mDogInfo.setText(dogInfo);



        mTime = result.getDogInfo().getTodayTime();
        Log.e("mTime","왜"+mTime);
        if (mTime > 0) {
            mTimeTickin = ((double) mTime / (18));

            mStartWalking.setBackgroundResource(R.drawable.shape);
            mStartWalking.setText("추가 산책하기");
            mStartWalking.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDogDogBlue));
        } else {
            mTimeTickin = 0;
            mStartWalking.setBackgroundResource(R.drawable.shape_full);
            mStartWalking.setText("산책 시작하기");
            mStartWalking.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        }
        mAimProgressBar.setProgress((int) (mTimeTickin * (double) 10));
        if (mPercent == -1) {
            mPercentHome.setText(String.valueOf(0));
            explainProgressbar.setText("지금 바로 산책을 시작해보세요!");
        } else if (mPercent > 0 || mPercent < 100) {
            mPercentHome.setText(String.valueOf(mPercent));
            explainProgressbar.setText("목표 산책량까지 좀 더 힘내봐요!");
        } else if (mPercent >= 100) {
            mPercentHome.setText(String.valueOf(mPercent));
            explainProgressbar.setText("일일 목표 산책량을 달성했어요!");
        }

        Log.e("dogUrl", result.getDogInfo().getDogImg());
        if (result.getDogInfo().getDogImg() != null) {


            if (!result.getDogInfo().getDogImg().equals("https://firebasestorage.googleapis.com/v0/b/dogdog-1d2f8.appspot.com/o/default_profile_image.png?alt=media&token=9052b50b-dd25-46b1-ba22-f1581a1231f5")) {

                mSmallestCamera.setVisibility(View.INVISIBLE);
            } else {
                mSmallestCamera.setVisibility(View.VISIBLE);
            }


            showDogDogLoadingDialog();
            Glide.with(this)
                    .load(result.getDogInfo().getDogImg())
                    .circleCrop()
                    .override(54, 54) // ex) override(600, 200)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                            hideDogDogLoadingDialog();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            hideDogDogLoadingDialog();
                            return false;
                        }
                    })

                    .into(defaultDogImage);
//            https://stackoverflow.com/questions/25278821/how-to-round-an-image-with-glide-library
            mDogIdx = result.getDogInfo().getDogIdx();
        }
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt("dogIdx", mDogIdx);

        editor.apply();


    }


}