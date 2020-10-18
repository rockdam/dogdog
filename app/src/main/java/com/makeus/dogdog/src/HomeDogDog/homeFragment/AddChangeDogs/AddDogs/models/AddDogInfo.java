package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDogInfo {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birth")
    @Expose
    private String birth;
    @SerializedName("weight")
    @Expose
    private Float weight;
    @SerializedName("breedIdx")
    @Expose
    private Integer breedIdx;
    @SerializedName("isDisplayed")
    @Expose
    private String isDisplayed;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getBreedIdx() {
        return breedIdx;
    }

    public void setBreedIdx(Integer breedIdx) {
        this.breedIdx = breedIdx;
    }

    public String getIsDisplayed() {
        return isDisplayed;
    }

    public void setIsDisplayed(String isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
