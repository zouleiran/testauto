package cn.boss.platform.bean.toolsManager;

import java.util.Date;

/**
 * Created by admin on 2019/12/19.
 */
public class AppInterfaceLogBean {

    private int id;
    private String url;
    private String parameter;
    private String result;
    private String phone;
    private String userId;
    private String method;
    private String status;
    private String businessStatus;
    private Date createTime;

    @Override
    public String toString() {
        return "AppInterfaceLogBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", parameter='" + parameter + '\'' +
                ", result='" + result + '\'' +
                ", phone='" + phone + '\'' +
                ", userId='" + userId + '\'' +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                ", businessStatus='" + businessStatus + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}