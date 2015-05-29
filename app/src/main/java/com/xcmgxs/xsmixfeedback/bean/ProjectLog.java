package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.File;

/**
 * Created by zhangyi on 2015-3-20.
 */
@SuppressWarnings("serial")
public class ProjectLog extends Entity {

    @JsonProperty("AuthorID")
    private int authorid;

    @JsonProperty("Author")
    private User author;

    @JsonProperty("LogContent")
    private String content;

    @JsonProperty("CreateDate")
    private String createdate;

    @JsonProperty("Projectid")
    private String projectid;

    @JsonProperty("Project")
    private Project project;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("ImageFile")
    private File imagefile;

    @JsonProperty("AMRFile")
    private File amrfile;

    @JsonProperty("LogPic")
    private String pic;


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public File getImagefile() {
        return imagefile;
    }

    public void setImagefile(File imagefile) {
        this.imagefile = imagefile;
    }

    public File getAmrfile() {
        return amrfile;
    }

    public void setAmrfile(File amrfile) {
        this.amrfile = amrfile;
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
