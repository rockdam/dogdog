package com.makeus.dogdog.src.HomeDogDog.homeFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DogInfo {

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
    private Integer goal;
    @SerializedName("acheivedGoal")
    @Expose
    private Integer acheivedGoal;

    public Integer getTodayTime() {
        return todayTime;
    }

    public void setTodayTime(Integer todayTime) {
        this.todayTime = todayTime;
    }

    @SerializedName("todayTime")
    @Expose
    private Integer todayTime;

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

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Integer getAcheivedGoal() {
        return acheivedGoal;
    }

    public void setAcheivedGoal(Integer acheivedGoal) {
        this.acheivedGoal = acheivedGoal;
    }
}
