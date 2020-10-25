package com.makeus.dogdog.src.HomeDogDog.RankingFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankingResult {
    @SerializedName("ranking")
    @Expose
    private List<RankingData> ranking = null;
    @SerializedName("myRanking")
    @Expose
    private MyRanking myRanking;

    public List<RankingData> getRanking() {
        return ranking;
    }

    public void setRanking(List<RankingData> ranking) {
        this.ranking = ranking;
    }

    public MyRanking getMyRanking() {
        return myRanking;
    }

    public void setMyRanking(MyRanking myRanking) {
        this.myRanking = myRanking;
    }

}
