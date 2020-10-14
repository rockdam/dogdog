package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    public Result(Integer dogIdx, String imgUrl, String name, String isDisplayed) {
        this.dogIdx = dogIdx;
        this.imgUrl = imgUrl;
        this.name = name;
        this.isDisplayed = isDisplayed;
    }

    @SerializedName("dogIdx")
        @Expose
        private Integer dogIdx;
        @SerializedName("imgUrl")
        @Expose
        private String imgUrl;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("isDisplayed")
        @Expose
        private String isDisplayed;

        public Integer getDogIdx() {
            return dogIdx;
        }

        public void setDogIdx(Integer dogIdx) {
            this.dogIdx = dogIdx;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIsDisplayed() {
            return isDisplayed;
        }

        public void setIsDisplayed(String isDisplayed) {
            this.isDisplayed = isDisplayed;
        }


}
