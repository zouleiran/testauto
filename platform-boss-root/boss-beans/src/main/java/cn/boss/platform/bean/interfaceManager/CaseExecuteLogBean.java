package cn.boss.platform.bean.interfaceManager;

/**
 * Created by Asen on 2017/10/28.
 */
import java.util.Date;

public class CaseExecuteLogBean {

    private Integer id;
    private Integer projectId;
    private Integer groupId;
    private Integer interfaceId;
    private Integer envId;
    private Integer serialId;
    private Integer caseId;
    private Integer taskId;
    private Integer batchId;

    private String url;

    private String responseResult;
    private int responseStatus;
    private String parameters;
    private int expectedStatus;
    private String expectedResult;

    private Date createTime;
    private long execTime;
    private boolean result;
    private String author;

    private String path;

    @Override
    public String toString() {
        return "CaseExecuteLogBean{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", interfaceId=" + interfaceId +
                ", envId=" + envId +
                ", serialId=" + serialId +
                ", caseId=" + caseId +
                ", taskId=" + taskId +
                ", batchId=" + batchId +
                ", url='" + url + '\'' +
                ", responseResult='" + responseResult + '\'' +
                ", responseStatus=" + responseStatus +
                ", parameters='" + parameters + '\'' +
                ", expectedStatus=" + expectedStatus +
                ", expectedResult='" + expectedResult + '\'' +
                ", createTime=" + createTime +
                ", execTime=" + execTime +
                ", result=" + result +
                ", author='" + author + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
