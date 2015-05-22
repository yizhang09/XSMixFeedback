package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by zhangyi on 2015-05-14.
 */
public class Result implements Serializable {

    @JsonProperty("ErrorCode")
    private int errorCode;

    @JsonProperty("ErrorMessage")
    private String errorMessage;

    public boolean OK() {
        return errorCode == 1;
    }

    public int getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    @Override
    public String toString(){
        return String.format("RESULT: CODE:%d,MSG:%s", errorCode, errorMessage);
    }
}
