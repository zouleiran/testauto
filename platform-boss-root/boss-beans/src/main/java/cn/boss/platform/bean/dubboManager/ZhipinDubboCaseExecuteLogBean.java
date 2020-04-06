package cn.boss.platform.bean.dubboManager;

import java.util.Date;

/**
 * Created by admin on 2018/12/25.
 */
public class ZhipinDubboCaseExecuteLogBean {

    private Integer id;
    private Integer interfaceId;
    private Integer envId;
    private Integer serialId;
    private Integer caseId;
    private Integer taskId;

    private String responseResult;
    private String parameters;
    private String expectedResult;

    private Date createTime;
    private long execTime;
    private boolean result;
    private String author;

    private ZhipinEnvBean zhipinEnvBean;

    @Override
    public String toString() {
        return "ZhipinDubboCaseExecuteLogBean{" +
                "id=" + id +
                ", interfaceId=" + interfaceId +
                ", envId=" + envId +
                ", serialId=" + serialId +
                ", caseId=" + caseId +
                ", taskId=" + taskId +
                ", responseResult='" + responseResult + '\'' +
                ", parameters='" + parameters + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", createTime=" + createTime +
                ", execTime=" + execTime +
                ", result=" + result +
                ", author='" + author + '\'' +
                ", zhipinEnvBean=" + zhipinEnvBean +
                '}';
    }

    public ZhipinEnvBean getZhipinEnvBean() {
        return zhipinEnvBean;
    }

    public void setZhipinEnvBean(ZhipinEnvBean zhipinEnvBean) {
        this.zhipinEnvBean = zhipinEnvBean;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
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
