package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhangyi on 2015-05-28.
 */
public class ProjectFile extends Entity {

    @JsonProperty("ProjectID")
    private int projectid;

    @JsonProperty("Project")
    private Project project;

    @JsonProperty("FileName")
    private String filename;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("UploadDate")
    private String uploaddate;

    @JsonProperty("UploaderID")
    private int uploaderid;

    @JsonProperty("Uploader")
    private User uploader;

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public int getUploaderid() {
        return uploaderid;
    }

    public void setUploaderid(int uploaderid) {
        this.uploaderid = uploaderid;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
}
