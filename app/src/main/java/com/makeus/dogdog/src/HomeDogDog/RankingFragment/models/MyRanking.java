package com.makeus.dogdog.src.HomeDogDog.RankingFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyRanking {

    @SerializedName("dogRank")
    @Expose
    private String dogRank;
    @SerializedName("userIdx")
    @Expose
    private Integer userIdx;
    @SerializedName("dogIdx")
    @Expose
    private Integer dogIdx;
    @SerializedName("dogImg")
    @Expose
    private String dogImg;
    @SerializedName("dogName")
    @Expose
    private String dogName;
    @SerializedName("walkingCnt")
    @Expose
    private String walkingCnt;
    @SerializedName("walkingTime")
    @Expose
    private String walkingTime;

    public String getDogRank() {
        return dogRank;
    }

    public void setDogRank(String dogRank) {
        this.dogRank = dogRank;
    }

    public Integer getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(Integer userIdx) {
        this.userIdx = userIdx;
    }

    public Integer getDogIdx() {
        return dogIdx;
    }

    public void setDogIdx(Integer dogIdx) {
        this.dogIdx = dogIdx;
    }

    public String getDogImg() {
        return dogImg;
    }

    public void setDogImg(String dogImg) {
        this.dogImg = dogImg;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getWalkingCnt() {
        return walkingCnt;
    }

    public void setWalkingCnt(String walkingCnt) {
        this.walkingCnt = walkingCnt;
    }

    public String getWalkingTime() {
        return walkingTime;
    }

    public void setWalkingTime(String walkingTime) {
        this.walkingTime = walkingTime;
    }
}
