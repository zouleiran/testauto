package cn.boss.platform.web.dubboManager.form;
/**
 * Created by admin on 2018/12/25.
 */
public class ZhipinExecuteForm {

    private Integer interfaceId;
    private Integer envId;
    private Integer caseId;
    private Integer type;
    private String taskId;
    private String serialId;
    private String author;


    @Override
    public String toString() {
        return "ZhipinExecuteForm{" +
                "interfaceId=" + interfaceId +
                ", envId=" + envId +
                ", caseId=" + caseId +
                ", type=" + type +
                ", taskId='" + taskId + '\'' +
                ", serialId='" + serialId + '\'' +
                ", author='" + author + '\'' +
                '}';
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

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
