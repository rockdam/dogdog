package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalkingMonthResult {

    @SerializedName("days")
    @Expose
    private List<Integer> days = null;

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }
}
