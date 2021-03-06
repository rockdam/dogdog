package com.makeus.dogdog.src.HomeDogDog.FeedFragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.FeedFragment.AddFeedActivity.AddFeedActivity;
import com.makeus.dogdog.src.HomeDogDog.HomeActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Feed#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Feed extends Fragment {

    RecyclerView mFeedRecyclerView;
    ArrayList<FeedDatalist> mFeedDataList;
    FeedAdapter mFeedAdapter;
    ImageView addFeed;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment feed.
     */
    // TODO: Rename and change types and number of parameters
    public static Feed newInstance(String param1, String param2) {
        Feed fragment = new Feed();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Feed() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment

        mFeedDataList =new ArrayList<>();
        View view =inflater.inflate(R.layout.fragment_feed, container, false);

        addFeed=view.findViewById(R.id.addfeed_Feed);
        mFeedRecyclerView=view.findViewById(R.id.recyclerview_Feed);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        mFeedRecyclerView.setLayoutManager(linearLayoutManager);
        mFeedDataList.add(new FeedDatalist("비오는 날 산책 인증","비가 조금 내려서 우비 입히고 ","록",false));
        mFeedDataList.add(new FeedDatalist("비오는 날 산책 인증","비가 조금 내려서 우비 입히고 ","록",false));
        mFeedDataList.add(new FeedDatalist("비오는 날 산책 인증","비가 조금 내려서 우비 입히고 ","록",false));
        mFeedAdapter=new FeedAdapter(getContext(),mFeedDataList);

        mFeedRecyclerView.setAdapter(mFeedAdapter);


        addFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent((HomeActivity)getActivity(), AddFeedActivity.class);
                startActivity(intent);
//                // getActivity()로 MainActivity의 replaceFragment를 불러옵니다.
//                ((HomeActivity)getActivity()).replaceFragmentaddFeed(AddFeedFragment.newInstance());    // 새로 불러올 Fragment의 Instance를 Main으로 전달
            }

//            출처: https://mc10sw.tistory.com/16 [Make it possible]
            });

        return view;
    }
}