package com.makeus.dogdog.src.joinmember.step6;

public class Breeds {
    String breed;
    Integer breedIdx;


    public Breeds(String breed, Integer breedIdx) {
        this.breed = breed;
        this.breedIdx = breedIdx;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getBreedIdx() {
        return breedIdx;
    }

    public void setBreedIdx(Integer breedIdx) {
        this.breedIdx = breedIdx;
    }
}
