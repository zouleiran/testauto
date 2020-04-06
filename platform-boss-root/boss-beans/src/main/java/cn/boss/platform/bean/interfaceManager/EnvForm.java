package cn.boss.platform.bean.interfaceManager;

import java.util.Date;

/**
 * Created by admin on 2018/6/6.
 */
public class EnvForm {

    private Integer id;
    private Integer item;
    private Integer projectId;
    private String env;
    private String domain;
    private String ip;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "EnvBean{" +
                "id=" + id +
                ", item=" + item +
                ", projectId=" + projectId +
                ", env='" + env + '\'' +
                ", domain='" + domain + '\'' +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

