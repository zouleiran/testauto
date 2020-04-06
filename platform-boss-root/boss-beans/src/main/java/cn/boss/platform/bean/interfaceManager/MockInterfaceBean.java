package cn.boss.platform.bean.interfaceManager;

import java.util.Date;

/**
 * Created by admin on 2019/3/21.
 */
public class MockInterfaceBean {

    private Integer id;
    private Integer interfaceId;
    private String name;
    private String domain;
    private String url;
    private String method;
    private String parameters;
    private String status;
    private String responseResult;
    private String delay;
    private String author;
    private Date createTime;
    private Date updateTime;


    @Override
    public String toString() {
        return "MockInterfaceBean{" +
                "id=" + id +
                ", interfaceId=" + interfaceId +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", parameters='" + parameters + '\'' +
                ", status='" + status + '\'' +
                ", responseResult='" + responseResult + '\'' +
                ", delay='" + delay + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
