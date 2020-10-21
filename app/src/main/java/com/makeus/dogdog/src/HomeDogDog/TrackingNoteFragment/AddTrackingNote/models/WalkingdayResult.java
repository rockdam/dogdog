package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalkingdayResult {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("walkingDistance")
    @Expose
    private String walkingDistance;
    @SerializedName("walkingTime")
    @Expose
    private String walkingTime;
    @SerializedName("percent")
    @Expose
    private String percent;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWalkingDistance() {
        return walkingDistance;
    }

    public void setWalkingDistance(String walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

    public String getWalkingTime() {
        return walkingTime;
    }

    public void setWalkingTime(String walkingTime) {
        this.walkingTime = walkingTime;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
