package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by zhangyi on 2015-05-21.
 */
@SuppressWarnings("serial")
public class Department extends Entity {

    @JsonProperty("BName")
    private String name;

    @JsonProperty("BMiaoShu")
    private String describe;

    @JsonProperty("BPid")
    private int parentid;

    @JsonProperty("BSort")
    private int sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String descripe) {
        this.describe = descripe;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
