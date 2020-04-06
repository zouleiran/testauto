package cn.boss.platform.web.model;

import java.util.Date;

/**
 * Created by caosenquan on 2017/11/28.
 */
public class ExecuteLogViewModel {

    private int id;
    private String responseResult;
    private int responseStatus;
    private Date createTime;
    private long execTime;
    private String parameters;
    private int expectedStatus;
    private String expectedResult;
    private boolean result;
    private int serialId;
    private int taskId;
    private String taskName;
    private int caseId;
    private String url;
    private String author;

    private String resultStatus;
    private String scale;

    @Override
    public String toString() {
        return "ExecuteLogViewModel{" +
                "id=" + id +
                ", responseResult='" + responseResult + '\'' +
                ", responseStatus=" + responseStatus +
                ", createTime=" + createTime +
                ", execTime=" + execTime +
                ", parameters='" + parameters + '\'' +
                ", expectedStatus=" + expectedStatus +
                ", expectedResult='" + expectedResult + '\'' +
                ", result=" + result +
                ", serialId=" + serialId +
                ", taskId=" + taskId +
                ", taskName=" + taskName +
                ", caseId=" + caseId +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", scale='" + scale + '\'' +
                '}';
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
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

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public int getExpectedStatus() {
        return expectedStatus;
    }

    public void setExpectedStatus(int expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }
}
