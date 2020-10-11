package com.makeus.dogdog.src.HomeDogDog.startWalking.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("walkingTime")
    @Expose
    private Integer walkingTime;
    @SerializedName("percent")
    @Expose
    private Integer percent;
    @SerializedName("distance")
    @Expose
    private Integer distance;

    public Integer getWalkingTime() {
        return walkingTime;
    }

    public void setWalkingTime(Integer walkingTime) {
        this.walkingTime = walkingTime;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

}