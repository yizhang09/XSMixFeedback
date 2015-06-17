package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhangyi on 2015-4-22.
 */
public class ProjectIssue extends Entity {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Content")
    private String content;

    @JsonProperty("ProjectID")
    private String projectid;

    @JsonProperty("Project")
    private Project project;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("CreateDate")
    private String createdate;

    @JsonProperty("Creator")
    private User creator;

    @JsonProperty("CreatorID")
    private String creatorid;

    @JsonProperty("Pic1")
    private String pic1;


    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
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

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User createor) {
        this.creator = createor;
    }


}
