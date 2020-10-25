package com.makeus.dogdog.src.HomeDogDog.RankingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.interfaces.RankingView;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.models.RankingResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ranking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ranking extends Fragment implements RankingView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageView winnerImage;
    TextView winnercompletedNum, winnerSuccessRatio, winnerName;

    RankingService rankingService;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Ranking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ranking.
     */
    // TODO: Rename and change types and number of parameters
    public static Ranking newInstance(String param1, String param2) {
        Ranking fragment = new Ranking();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ranking, container, false);
        winnerImage = v.findViewById(R.id.winner_dog_img_ranking);
        winnercompletedNum = v.findViewById(R.id.winner_dog_completed_num_ranking);
        winnerName = v.findViewById(R.id.winner_dog_name_ranking);
        winnerSuccessRatio = v.findViewById(R.id.winner_dog_success_ratio_ranking);
        rankingService=new RankingService(this);
        rankingService.refreshRankingView();



        return v;
    }

    @Override
    public void refreshRanking(RankingResult rankingResult) {
        if(rankingResult!=null) {
            Glide.with(this)
                    .load(rankingResult.getRanking().get(0))
                    .circleCrop()
                    .override(54, 54) // ex) override(600, 200)
                    .into(winnerImage);
            winnerName.setText(rankingResult.getRanking().get(0).getDogName());
        }


    }
}