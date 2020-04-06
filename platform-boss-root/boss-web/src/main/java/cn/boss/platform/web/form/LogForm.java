package cn.boss.platform.web.form;

/**
 * Created by caosenquan on 2017/11/29.
 */
public class LogForm {

    private Integer taskId;
    private Integer projectId;
    private Integer serialId;
    private int pageIndex;
    private int pageSize;
    private String startTime;
    private String endTime;


    @Override
    public String toString() {
        return "LogForm{" +
                "taskId=" + taskId +
                ", projectId=" + projectId +
                ", serialId=" + serialId +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
