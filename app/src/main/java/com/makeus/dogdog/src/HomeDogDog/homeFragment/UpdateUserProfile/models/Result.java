package com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    @SerializedName("userIdx")
    @Expose
    private Integer userIdx;
    @SerializedName("nickName")
    @Expose
    private String nickName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dogInfo")
    @Expose
    private DogInfo dogInfo;

    public Integer getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(Integer userIdx) {
        this.userIdx = userIdx;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DogInfo getDogInfo() {
        return dogInfo;
    }

    public void setDogInfo(DogInfo dogInfo) {
        this.dogInfo = dogInfo;
    }
}
