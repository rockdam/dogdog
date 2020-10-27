package com.makeus.dogdog.src.HomeDogDog.RankingFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.HomeDogDog.RankingFragment.models.RankingData;

import java.util.ArrayList;

public class AdapterRanking  extends RecyclerView.Adapter<AdapterRanking.ItemHolder> {



    ArrayList<RankingData> rankingRecyclerViewData;

    Context mContext;
    LayoutInflater inflater;

    public AdapterRanking(Context context, ArrayList<RankingData> rankingRecyclerViewData) {
        this.rankingRecyclerViewData = rankingRecyclerViewData;
        this.mContext=context;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view =inflater.inflate(R.layout.item_layout_ranking,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {


        holder.onBind(rankingRecyclerViewData.get(position));
    }

    @Override
    public int getItemCount() {
        return rankingRecyclerViewData.size();
    }

    void clear(int position)
    {

        rankingRecyclerViewData.remove(position);
    }
   public class ItemHolder extends RecyclerView.ViewHolder {


        TextView rank,name,walkingcnt,walkingtime;
        ImageView dogImage;

       public ItemHolder(@NonNull View view) {

           super(view);
           rank=view.findViewById(R.id.rank_itemlayout);
           name=view.findViewById(R.id.name_itemlayout);
           walkingcnt=view.findViewById(R.id.walkingcnt_itemlayout);
           dogImage=view.findViewById(R.id.profileImage_itemlayout);
           walkingtime=view.findViewById(R.id.walking_time_ranking);


       }


       void onBind(RankingData rankingRecyclerViewData){


           rank.setText(""+rankingRecyclerViewData.getDogRank());
           walkingcnt.setText(""+rankingRecyclerViewData.getWalkingCnt());
           name.setText(""+rankingRecyclerViewData.getDogName());
           Glide.with(mContext)
                   .load(rankingRecyclerViewData.getDogImg())
                   .circleCrop()
                   .override(54, 54); // ex) override(600, 200)

           walkingtime.setText(""+rankingRecyclerViewData.getWalkingTime());

       }
   }
}
