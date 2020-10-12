package com.makeus.dogdog.src.HomeDogDog.startWalking.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopWalkingBody {
    @SerializedName("dogIdx")
    @Expose
    private Integer dogIdx;
    @SerializedName("walkingTime")
    @Expose
    private Integer walkingTime;
    @SerializedName("walkingDistance")
    @Expose
    private Integer walkingDistance;


    public Integer getDogIdx() {
        return dogIdx;
    }

    public void setDogIdx(Integer dogIdx) {
        this.dogIdx = dogIdx;
    }

    public Integer getWalkingTime() {
        return walkingTime;
    }

    public void setWalkingTime(Integer walkingTime) {
        this.walkingTime = walkingTime;
    }

    public Integer getWalkingDistance() {
        return walkingDistance;
    }

    public void setWalkingDistance(Integer walkingDistance) {
        this.walkingDistance = walkingDistance;
    }
}
