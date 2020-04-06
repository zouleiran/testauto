package cn.boss.platform.bean.autoManager;

import java.util.Date;

/**
 * Created by admin on 2019/11/21.
 */
public class AutoCaseExecuteLogBean {

    private Integer id;
    private Integer projectId;
    private Integer caseId;
    private Integer caseStepId;
    private String stepName;
    private Integer serialId;
    private Integer taskId;

    private String responseResult;
    private String expectedResult;
    private String parameter;
    private Date createTime;
    private long execTime;
    private Integer success;
    private String author;

    @Override
    public String toString() {
        return "AutoCaseExecuteLogBean{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", caseId=" + caseId +
                ", caseStepId=" + caseStepId +
                ", stepName='" + stepName + '\'' +
                ", serialId=" + serialId +
                ", taskId=" + taskId +
                ", responseResult='" + responseResult + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", parameter='" + parameter + '\'' +
                ", createTime=" + createTime +
                ", execTime=" + execTime +
                ", success=" + success +
                ", author='" + author + '\'' +
                '}';
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getCaseStepId() {
        return caseStepId;
    }

    public void setCaseStepId(Integer caseStepId) {
        this.caseStepId = caseStepId;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getExecTime() {
        return execTime;
    }

    public void setExecTime(long execTime) {
        this.execTime = execTime;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}