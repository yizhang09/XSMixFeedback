package com.xcmgxs.xsmixfeedback.bean;

/**
 * Created by zhangyi on 2015-05-14.
 */
public class Result {
    private int errorCode;
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
