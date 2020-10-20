package com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendImagData {

    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("dogName")
    @Expose
    private String dogName;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }
}
