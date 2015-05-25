package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by zhangyi on 2015-05-25.
 */
@SuppressWarnings("serial")
public class Notification implements Serializable {

    @JsonProperty("MsgCount")
    private int msgCount;

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
}
