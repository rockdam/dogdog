package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shrikanthravi on 06/03/18.
 */

public class DayHistory {

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
