package com.makeus.dogdog.src.HomeDogDog.MypageFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPageRanking {

    @SerializedName("dogIdx")
    @Expose
    private Integer dogIdx;
    @SerializedName("dogImg")
    @Expose
    private String dogImg;
    @SerializedName("dogName")
    @Expose
    private String dogName;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("breed")
    @Expose
    private String breed;
    @SerializedName("goal")
    @Expose
    private String goal;
    @SerializedName("walkingCnt")
    @Expose
    private String walkingCnt;
    @SerializedName("myRanking")
    @Expose
    private String myRanking;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getWalkingCnt() {
        return walkingCnt;
    }

    public void setWalkingCnt(String walkingCnt) {
        this.walkingCnt = walkingCnt;
    }

    public String getMyRanking() {
        return myRanking;
    }

    public void setMyRanking(String myRanking) {
        this.myRanking = myRanking;
    }
}
