package cn.boss.platform.bean.toolsManager;

import java.util.Date;

/**
 * Created by admin on 2019/9/4.
 */
public class PhoneManagerBean {

    private Integer id;
    private String number;
    private String phone;
    private String type;
    private String os;
    private String crack;
    private String resolution;
    private String size;
    private String networkModel;
    private String receiver;
    private String imei;
    private String user;
    private String department;
    private String remarks;
    private Date updateTime;


    @Override
    public String toString() {
        return "PhoneManagerBean{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                ", os='" + os + '\'' +
                ", crack='" + crack + '\'' +
                ", resolution='" + resolution + '\'' +
                ", size='" + size + '\'' +
                ", networkModel='" + networkModel + '\'' +
                ", receiver='" + receiver + '\'' +
                ", imei='" + imei + '\'' +
                ", user='" + user + '\'' +
                ", department='" + department + '\'' +
                ", remarks='" + remarks + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCrack() {
        return crack;
    }

    public void setCrack(String crack) {
        this.crack = crack;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNetworkModel() {
        return networkModel;
    }

    public void setNetworkModel(String networkModel) {
        this.networkModel = networkModel;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}