package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalkingdayResult {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("walkingDistance")
    @Expose
    private Integer walkingDistance;
    @SerializedName("walkingTime")
    @Expose
    private Integer walkingTime;
    @SerializedName("percent")
    @Expose
    private Integer percent;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getWalkingDistance() {
        return walkingDistance;
    }

    public void setWalkingDistance(Integer walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

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
}
