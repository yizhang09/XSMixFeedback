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

    @JsonProperty("LogPic1")
    private String pic1;

    @JsonProperty("LogPic2")
    private String pic2;

    @JsonProperty("LogPic3")
    private String pic3;

    @JsonProperty("LogPic4")
    private String pic4;

    @JsonProperty("LogPic5")
    private String pic5;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Step")
    private String step;

    @JsonProperty("pstate")
    private String pstate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

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

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic) {
        this.pic1 = pic;
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
