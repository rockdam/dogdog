package com.makeus.dogdog.src.joinmember.step6.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchBreedsResult {

    @SerializedName("breedIdx")
    @Expose
    private Integer breedIdx;
    @SerializedName("breed")
    @Expose
    private String breed;

    public Integer getBreedIdx() {
        return breedIdx;
    }

    public void setBreedIdx(Integer breedIdx) {
        this.breedIdx = breedIdx;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

}