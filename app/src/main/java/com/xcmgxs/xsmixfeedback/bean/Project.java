package com.xcmgxs.xsmixfeedback.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created by zhangyi on 2015-3-20.
 */
@SuppressWarnings("serial")
public class Project extends Entity {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("batch")
    private String batch;

    @JsonProperty("num")
    private Integer num;

    @JsonProperty("base")
    private String base;

    @JsonProperty("tank")
    private String tank;

    @JsonProperty("sendstate")
    private String sendstate;

    @JsonProperty("debug")
    private String debug;

    @JsonProperty("packag")
    private String packag;

    @JsonProperty("emState")
    private String emState;

    @JsonProperty("persons")
    private String persons;

    @JsonProperty("packagedate")
    private String packagedate;

    @JsonProperty("basedate")
    private String basedate;

    @JsonProperty("senddate")
    private String senddate;

    @JsonProperty("installdate")
    private String installdate;

    @JsonProperty("address")
    private String address;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("contactno")
    private String contactno;

    @JsonProperty("createon")
    private Date createon;

    @JsonProperty("updateon")
    private Date updateon;

    @JsonProperty("manager")
    private String manager;

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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getTank() {
        return tank;
    }

    public void setTank(String tank) {
        this.tank = tank;
    }

    public String getSendstate() {
        return sendstate;
    }

    public void setSendstate(String sendstate) {
        this.sendstate = sendstate;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getPackag() {
        return packag;
    }

    public void setPackag(String packag) {
        this.packag = packag;
    }

    public String getEmState() {
        return emState;
    }

    public void setEmState(String emState) {
        this.emState = emState;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getPackagedate() {
        return packagedate;
    }

    public void setPackagedate(String packagedate) {
        this.packagedate = packagedate;
    }

    public String getBasedate() {
        return basedate;
    }

    public void setBasedate(String basedate) {
        this.basedate = basedate;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getInstalldate() {
        return installdate;
    }

    public void setInstalldate(String installdate) {
        this.installdate = installdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public Date getCreateon() {
        return createon;
    }

    public void setCreateon(Date createon) {
        this.createon = createon;
    }

    public Date getUpdateon() {
        return updateon;
    }

    public void setUpdateon(Date updateon) {
        this.updateon = updateon;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
