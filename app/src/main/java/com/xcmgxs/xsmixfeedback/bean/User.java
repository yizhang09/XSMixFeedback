package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created by zhangyi on 2015-3-18.
 */
@SuppressWarnings("serial")
public class User extends Entity {

    @JsonProperty("ULoginName")
    private String username;

    @JsonProperty("UName")
    private String name;

    @JsonProperty("PhoneNo")
    private String phoneNumber;

    @JsonProperty("UPwd")
    private String password;

    @JsonProperty("UEnable")
    private boolean enabled;

    @JsonProperty("OfficePhone")
    private String officeNumber;

    @JsonProperty("USort")
    private int usersort;

    @JsonProperty("AddTime")
    private Date addTime;

    @JsonProperty("Portrait")
    private String portrait;// 头像



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public int getUsersort() {
        return usersort;
    }

    public void setUsersort(int usersort) {
        this.usersort = usersort;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }





}
