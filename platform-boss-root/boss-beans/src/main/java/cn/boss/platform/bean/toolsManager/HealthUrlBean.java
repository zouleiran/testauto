package cn.boss.platform.bean.toolsManager;

import java.util.Date;

/**
 * Created by admin on 2019/2/26.
 */
public class HealthUrlBean {

    Integer id;
    Integer serialId;
    String env;
    String person;
    String url;
    String domain;
    Integer status;
    String zhipinName;
    String responseResult;
    private Date createTime;

    @Override
    public String toString() {
        return "HealthUrlBean{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", env='" + env + '\'' +
                ", person='" + person + '\'' +
                ", url='" + url + '\'' +
                ", domain='" + domain + '\'' +
                ", status=" + status +
                ", zhipinName='" + zhipinName + '\'' +
                ", responseResult='" + responseResult + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getZhipinName() {
        return zhipinName;
    }

    public void setZhipinName(String zhipinName) {
        this.zhipinName = zhipinName;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
