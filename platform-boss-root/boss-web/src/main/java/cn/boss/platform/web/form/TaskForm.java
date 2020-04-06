package cn.boss.platform.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * Created by admin on 2018/8/21.
 */
public class TaskForm {

    private Integer id;
    @Range(min=1,max=999999,message = "项目名称不能为空!")
    private Integer projectId;
    private Integer envId;

    private String groupId;
    private String caseId;
    private String interfaceId;
    @NotBlank(message = "任务名不能为空!")
    private String taskName;
    private String type;
    private String version;
    private Integer caseCount;
    private String message;
    @NotBlank(message = "添加人员不能为空!")
    private String author;
    private Date createTime;
    private Date updateTime;
    private String cron;
    private String jobstatus;


    @Override
    public String toString() {
        return "TaskForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", envId=" + envId +
                ", groupId='" + groupId + '\'' +
                ", caseId='" + caseId + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", caseCount=" + caseCount +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", cron='" + cron + '\'' +
                ", jobstatus='" + jobstatus + '\'' +
                '}';
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

    public String getcron() {
        return cron;
    }

    public void setcron(String cron) {
        this.cron = cron;
    }
    public String getjobstatus() {
        return jobstatus;
    }

    public void setjobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
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
