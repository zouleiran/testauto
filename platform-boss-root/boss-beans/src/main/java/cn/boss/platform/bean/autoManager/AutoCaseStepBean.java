package cn.boss.platform.bean.autoManager;

import java.util.Date;

/**
 * Created by admin on 2019/11/8.
 */
public class AutoCaseStepBean {

    private Integer id;
    private Integer index;
    private Integer projectId;
    private Integer groupId;
    private Integer caseId;
    private String name;
    private String action;
    private String parameter;
    private String exception;

    private String value;
    private String message;
    private Date createTime;
    private Date updateTime;


    @Override
    public String toString() {
        return "AutoCaseStepBean{" +
                "id=" + id +
                ", index=" + index +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", caseId=" + caseId +
                ", name='" + name + '\'' +
                ", action='" + action + '\'' +
                ", parameter='" + parameter + '\'' +
                ", exception='" + exception + '\'' +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
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

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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