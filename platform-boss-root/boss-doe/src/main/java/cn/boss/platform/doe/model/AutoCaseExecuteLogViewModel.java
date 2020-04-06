package cn.boss.platform.doe.model;

import java.util.Date;

/**
 * Created by admin on 2019/11/26.
 */
public class AutoCaseExecuteLogViewModel {

    private int id;
    private Integer projectId;
    private int caseId;
    private int caseStepId;
    private int taskId;
    private int serialId;
    private String expectedResult;
    private String responseResult;
    private String parameter;
    private Date createTime;
    private Integer success;
    private long execTime;
    private String author;

    private String taskName;
    private String stepName;

    private String resultStatus;
    private String scale;

    @Override
    public String toString() {
        return "AutoCaseExecuteLogViewModel{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", caseId=" + caseId +
                ", caseStepId=" + caseStepId +
                ", taskId=" + taskId +
                ", serialId=" + serialId +
                ", expectedResult='" + expectedResult + '\'' +
                ", responseResult='" + responseResult + '\'' +
                ", parameter='" + parameter + '\'' +
                ", createTime=" + createTime +
                ", success=" + success +
                ", execTime=" + execTime +
                ", author='" + author + '\'' +
                ", taskName='" + taskName + '\'' +
                ", stepName='" + stepName + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", scale='" + scale + '\'' +
                '}';
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getCaseStepId() {
        return caseStepId;
    }

    public void setCaseStepId(int caseStepId) {
        this.caseStepId = caseStepId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
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

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public long getExecTime() {
        return execTime;
    }

    public void setExecTime(long execTime) {
        this.execTime = execTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }
}