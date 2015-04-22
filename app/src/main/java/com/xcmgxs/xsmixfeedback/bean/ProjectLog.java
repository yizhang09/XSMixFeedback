package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhangyi on 2015-3-20.
 */
@SuppressWarnings("serial")
public class ProjectLog extends Entity {

    @JsonProperty("Author")
    private String author;

    @JsonProperty("Content")
    private String content;

    @JsonProperty("CreateDate")
    private String createdate;

    @JsonProperty("Projectid")
    private String projectid;

    @JsonProperty("Title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }



}
