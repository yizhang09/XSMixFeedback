package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhangyi on 2015-3-18.
 */
@SuppressWarnings("serial")
public class User extends Entity {

    @JsonProperty("username")
    private String _username;

    @JsonProperty("name")
    private String _name;

    @JsonProperty("bio")
    private String _bio;

    @JsonProperty("state")
    private String _state;

    @JsonProperty("created_at")
    private String _created_at;

    @JsonProperty("portrait")
    private String _portrait;// 头像

    @JsonProperty("new_portrait")
    private String _new_portrait;// 新头像

    @JsonProperty("is_admin")
    private boolean _isAdmin;


    public String getUsername() {
        return _username;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getBio() {
        return _bio;
    }

    public void setBio(String _bio) {
        this._bio = _bio;
    }

    public String getState() {
        return _state;
    }

    public void setState(String _state) {
        this._state = _state;
    }

    public String getCreated_at() {
        return _created_at;
    }

    public void setCreated_at(String _created_at) {
        this._created_at = _created_at;
    }

    public String getPortrait() {
        return _portrait;
    }

    public void setPortrait(String _portrait) {
        this._portrait = _portrait;
    }

    public String getNew_portrait() {
        return _new_portrait;
    }

    public void setNew_portrait(String new_portrait) {
        this._new_portrait = new_portrait;
    }

    public boolean isIsAdmin() {
        return _isAdmin;
    }

    public void setIsAdmin(boolean _isAdmin) {
        this._isAdmin = _isAdmin;
    }

}
