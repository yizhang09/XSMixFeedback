package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhangyi on 2015-06-19.
 */
public class ProjectDoc extends Entity {

    @JsonProperty("ProjectID")
    private int projectid;

    @JsonProperty("Project")
    private Project project;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Pic")
    private String pic;

    @JsonProperty("Uploader")
    private User uploader;

    @JsonProperty("UploaderID")
    private int uploaderid;

    @JsonProperty("UploadDate")
    private String uploadDate;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getUploaderid() {
        return uploaderid;
    }

    public void setUploaderid(int uploaderid) {
        this.uploaderid = uploaderid;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
