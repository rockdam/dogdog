package com.makeus.dogdog.src.joinmember.step6.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchBreedsResponse {

    @SerializedName("result")
    @Expose
    private List<SearchBreedsResult> searchBreedsResult = null;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;

    public List<SearchBreedsResult> getSearchBreedsResult() {
        return searchBreedsResult;
    }

    public void setSearchBreedsResult(List<SearchBreedsResult> searchBreedsResult) {
        this.searchBreedsResult = searchBreedsResult;
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