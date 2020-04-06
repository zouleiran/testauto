package cn.boss.platform.web.form;

/**
 * Created by caosenquan on 2017/10/27.
 */
public class ExecuteParam {

    private Integer projectId;
    private String groupId;
    private String interfaceIds;
    private String caseIds;
    private Integer envId;
    private Integer batchId;
    private String taskId;
    private String version;
    private String taskName;
    private String serialId;
    private String taskType;
    private String author;


    @Override
    public String toString() {
        return "ExecuteParam{" +
                "projectId=" + projectId +
                ", groupId='" + groupId + '\'' +
                ", interfaceIds='" + interfaceIds + '\'' +
                ", caseIds='" + caseIds + '\'' +
                ", envId=" + envId +
                ", batchId=" + batchId +
                ", taskId='" + taskId + '\'' +
                ", version='" + version + '\'' +
                ", taskName='" + taskName + '\'' +
                ", serialId='" + serialId + '\'' +
                ", taskType='" + taskType + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getInterfaceIds() {
        return interfaceIds;
    }

    public void setInterfaceIds(String interfaceIds) {
        this.interfaceIds = interfaceIds;
    }

    public String getCaseIds() {
        return caseIds;
    }

    public void setCaseIds(String caseIds) {
        this.caseIds = caseIds;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
