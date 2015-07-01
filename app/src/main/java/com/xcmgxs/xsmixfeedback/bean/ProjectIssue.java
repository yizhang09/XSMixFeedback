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

    @JsonProperty("Reason")
    private String reason;

    @JsonProperty("Solution")
    private String solution;

    @JsonProperty("Responsible")
    private String responsible;

    @JsonProperty("CreateDate")
    private String createdate;

    @JsonProperty("Creator")
    private User creator;

    @JsonProperty("CreatorID")
    private String creatorid;

    @JsonProperty("Pic1")
    private String pic1;

    @JsonProperty("Pic2")
    private String pic2;

    @JsonProperty("Pic3")
    private String pic3;

    @JsonProperty("Pic4")
    private String pic4;

    @JsonProperty("Pic5")
    private String pic5;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

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
