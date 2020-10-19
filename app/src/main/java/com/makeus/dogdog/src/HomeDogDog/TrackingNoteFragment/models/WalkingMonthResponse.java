package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalkingMonthResponse {

    @SerializedName("result")
    @Expose
    private WalkingMonthResult result;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;

    public WalkingMonthResult getResult() {
        return result;
    }

    public void setResult(WalkingMonthResult result) {
        this.result = result;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
