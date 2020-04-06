package cn.boss.platform.bean.interfaceManager;

import java.util.Date;

/**
 * Created by admin on 2018/8/21.
 */
public class TaskBean {

    private Integer id;
    private Integer projectId;
    private String interfaceId;
    private Integer envId;
    private String groupId;
    private String caseId;
    private String taskName;
    private String type;
    private Integer caseCount;
    private String version;
    private String message;
    private String author;
    private String cron;
    private String jobstatus;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "TaskBean{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", interfaceId='" + interfaceId + '\'' +
                ", envId=" + envId +
                ", groupId='" + groupId + '\'' +
                ", caseId='" + caseId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", type='" + type + '\'' +
                ", caseCount=" + caseCount +
                ", version='" + version + '\'' +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                ", cron='" + cron + '\'' +
                ", jobstatus='" + jobstatus + '\'' +
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(Integer caseCount) {
        this.caseCount = caseCount;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
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
