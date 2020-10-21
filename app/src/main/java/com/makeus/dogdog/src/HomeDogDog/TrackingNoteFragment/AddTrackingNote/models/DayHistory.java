package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shrikanthravi on 06/03/18.
 */

public class DayHistory {

    private String date;
    private String content;
    private String ImgUrl;
    private int dogIdx;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDogIdx() {
        return dogIdx;
    }

    public void setDogIdx(int dogIdx) {
        this.dogIdx = dogIdx;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
