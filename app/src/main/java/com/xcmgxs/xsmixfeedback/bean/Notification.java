package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by zhangyi on 2015-05-25.
 */
@SuppressWarnings("serial")
public class Notification extends Entity implements Serializable {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Content")
    private String content;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("IsRead")
    private boolean isRead;

    @JsonProperty("isSend")
    private boolean isSend;

    @JsonProperty("CreatorID")
    private int creatorid;

    @JsonProperty("Creator")
    private User creator;

    @JsonProperty("CreateDate")
    private String createtime;

    public boolean isSend() {
        return isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
    }

    public int getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(int creatorid) {
        this.creatorid = creatorid;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
