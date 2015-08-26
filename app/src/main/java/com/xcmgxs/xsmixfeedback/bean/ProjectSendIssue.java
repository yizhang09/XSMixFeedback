package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhangyi on 2015-08-25.
 */
public class ProjectSendIssue extends Entity {

    @JsonProperty("ProjectID")
    private String projectid;

    @JsonProperty("Project")
    private Project project;

    @JsonProperty("MaterialNo")
    private String materialNo;

    @JsonProperty("MaterialName")
    private String materialName;

    @JsonProperty("WrongNum")
    private String wrongNum;

    @JsonProperty("ListNum")
    private String listNum;

    @JsonProperty("CreateDate")
    private String createdate;

    @JsonProperty("Creator")
    private User creator;

    @JsonProperty("CreatorID")
    private int creatorid;

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

    @JsonProperty("State")
    private String state;


    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getWrongNum() {
        return wrongNum;
    }

    public void setWrongNum(String wrongNum) {
        this.wrongNum = wrongNum;
    }

    public String getListNum() {
        return listNum;
    }

    public void setListNum(String listNum) {
        this.listNum = listNum;
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

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(int creatorid) {
        this.creatorid = creatorid;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
