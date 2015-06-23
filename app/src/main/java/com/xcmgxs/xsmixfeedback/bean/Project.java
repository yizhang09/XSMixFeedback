package com.xcmgxs.xsmixfeedback.bean;


import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangyi on 2015-3-20.
 */
@SuppressWarnings("serial")
public class Project extends Entity {

    @JsonProperty("PName")
    private String name;

    @JsonProperty("PType")
    private String type;

    @JsonProperty("PBacth")
    private String batch;

    @JsonProperty("PNum")
    private Integer num;

    @JsonProperty("PBase")
    private String base;

    @JsonProperty("PTank")
    private String tank;

    @JsonProperty("PSendState")
    private String sendstate;

    @JsonProperty("PDebug")
    private String debug;

    @JsonProperty("PPackag")
    private String packag;

    @JsonProperty("PPic")
    private String pic;

    @JsonProperty("PEMState")
    private String emState;

    @JsonProperty("PPersons")
    private String persons;

    @JsonProperty("PPackagDate")
    private String packagedate;

    @JsonProperty("PBaseDate")
    private String basedate;

    @JsonProperty("PSendDate")
    private String senddate;

    @JsonProperty("PInstallDate")
    private String installdate;

    @JsonProperty("PInstallOverDate1")
    private String installoverdate;

    @JsonProperty("PInstallOverDate2")
    private String installoverdate2;

    @JsonProperty("PDebugOverDate1")
    private String debugoverdate1;

    @JsonProperty("PDebugOverDate2")
    private String debugoverdate2;

    @JsonProperty("PEnterDate1")
    private String enterdate1;

    @JsonProperty("PEnterDate2")
    private String enterdate2;

    @JsonProperty("PDisChargDate1")
    private String dischargdate1;

    @JsonProperty("PDisChargDate2")
    private String dischargdate2;

    @JsonProperty("PCheckDate1")
    private String checkdate1;

    @JsonProperty("PCheckDate2")
    private String checkdate2;

    @JsonProperty("PAmounts1")
    private String amount1;

    @JsonProperty("PAmounts2")
    private String amount2;

    @JsonProperty("PAllAmounts1")
    private String allamount1;

    @JsonProperty("PAllAmounts2")
    private String allamount2;

    @JsonProperty("PAddress")
    private String address;

    @JsonProperty("PCustomer")
    private String customer;

    @JsonProperty("PContractNo")
    private String contactno;

    @JsonProperty("PState")
    private String state;

    @JsonProperty("PDelState")
    private String delstate;

    @JsonProperty("PCreatedOn")
    private Date createon;


    @JsonProperty("PManager")
    private int managerid;


    @JsonProperty("Manager")
    private User manager;


    @JsonProperty("UpdateTime")
    private Date updatetime;

    public Date getUpdatetime() {
        return updatetime;
    }

    public String getCCTUpdatetime() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.format(updatetime.getTime() - 8 * 60 * 60 * 1000);
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public int getManagerid() {
        return managerid;
    }

    public void setManagerid(int managerid) {
        this.managerid = managerid;
    }


    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
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

    public String getCCTCreateon() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        return df.format(createon.getTime() - 8 * 60 * 60 * 1000);
    }


    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getInstalloverdate() {
        return installoverdate;
    }

    public void setInstalloverdate(String installoverdate) {
        this.installoverdate = installoverdate;
    }

    public String getInstalloverdate2() {
        return installoverdate2;
    }

    public void setInstalloverdate2(String installoverdate2) {
        this.installoverdate2 = installoverdate2;
    }

    public String getDebugoverdate1() {
        return debugoverdate1;
    }

    public void setDebugoverdate1(String debugoverdate1) {
        this.debugoverdate1 = debugoverdate1;
    }

    public String getDebugoverdate2() {
        return debugoverdate2;
    }

    public void setDebugoverdate2(String debugoverdate2) {
        this.debugoverdate2 = debugoverdate2;
    }

    public String getEnterdate1() {
        return enterdate1;
    }

    public void setEnterdate1(String enterdate1) {
        this.enterdate1 = enterdate1;
    }

    public String getEnterdate2() {
        return enterdate2;
    }

    public void setEnterdate2(String enterdate2) {
        this.enterdate2 = enterdate2;
    }

    public String getDischargdate1() {
        return dischargdate1;
    }

    public void setDischargdate1(String dischargdate1) {
        this.dischargdate1 = dischargdate1;
    }

    public String getDischargdate2() {
        return dischargdate2;
    }

    public void setDischargdate2(String dischargdate2) {
        this.dischargdate2 = dischargdate2;
    }

    public String getCheckdate1() {
        return checkdate1;
    }

    public void setCheckdate1(String checkdate1) {
        this.checkdate1 = checkdate1;
    }

    public String getCheckdate2() {
        return checkdate2;
    }

    public void setCheckdate2(String checkdate2) {
        this.checkdate2 = checkdate2;
    }

    public String getAmount1() {
        return amount1;
    }

    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }

    public String getAmount2() {
        return amount2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }

    public String getAllamount1() {
        return allamount1;
    }

    public void setAllamount1(String allamount1) {
        this.allamount1 = allamount1;
    }

    public String getAllamount2() {
        return allamount2;
    }

    public void setAllamount2(String allamount2) {
        this.allamount2 = allamount2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDelstate() {
        return delstate;
    }

    public void setDelstate(String delstate) {
        this.delstate = delstate;
    }
}
