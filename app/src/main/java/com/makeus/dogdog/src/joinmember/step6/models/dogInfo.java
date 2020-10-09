package com.makeus.dogdog.src.joinmember.step6.models;

import java.io.Serializable;

public class dogInfo implements Serializable {
    String name;
    String gender;
    String birth ;
    int breedIdx;
    float weight;
    String imgUrl;

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

    public int getBreedIdx() {
        return breedIdx;
    }

    public void setBreedIdx(int breedIdx) {
        this.breedIdx = breedIdx;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
