package cn.boss.platform.bean.batchCaseManager;

import java.util.Date;

/**
 * Created by admin on 2018/9/6.
 */
public class BatchCaseBean {

    private Integer id;
    private Integer projectId;
    private Integer groupId;
    private Integer envId;

    private String author;
    private String name;
    private String message;
    private Date createTime;
    private Date updateTime;

    //用于获取项目组使用
    private String label;

    @Override
    public String toString() {
        return "BatchCaseBean{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", envId=" + envId +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", label='" + label + '\'' +
                '}';
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
