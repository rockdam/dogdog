package com.makeus.dogdog.src.HomeDogDog.homeFragment.UpdateUserProfile.models;

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
    @SerializedName("birth")
    @Expose
    private String birth;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("breed")
    @Expose
    private String breed;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("isDisplayed")
    @Expose
    private String isDisplayed;

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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getIsDisplayed() {
        return isDisplayed;
    }

    public void setIsDisplayed(String isDisplayed) {
        this.isDisplayed = isDisplayed;
    }
}
