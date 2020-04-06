package cn.boss.platform.bean.interfaceManager;

import java.util.List;

/**
 * Created by admin on 2018/8/25.
 */
public class ReportBean {

    private int interfaceId;
    private String interfaceName;
    private String path;
    private int interCaseSuccessCount;
    private int interCaseCount;
    private int interCasefailCount;
    private String successRate;
    private Long executeTime;
    private List<CaseLogResultBean> executeLogs;

    @Override
    public String toString() {
        return "ReportBean{" +
                "interfaceId=" + interfaceId +
                ", interfaceName='" + interfaceName + '\'' +
                ", path='" + path + '\'' +
                ", interCaseSuccessCount=" + interCaseSuccessCount +
                ", interCaseCount=" + interCaseCount +
                ", interCasefailCount=" + interCasefailCount +
                ", successRate='" + successRate + '\'' +
                ", executeTime=" + executeTime +
                ", executeLogs=" + executeLogs +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(int interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public int getInterCaseSuccessCount() {
        return interCaseSuccessCount;
    }

    public void setInterCaseSuccessCount(int interCaseSuccessCount) {
        this.interCaseSuccessCount = interCaseSuccessCount;
    }

    public int getInterCaseCount() {
        return interCaseCount;
    }

    public void setInterCaseCount(int interCaseCount) {
        this.interCaseCount = interCaseCount;
    }

    public int getInterCasefailCount() {
        return interCasefailCount;
    }

    public void setInterCasefailCount(int interCasefailCount) {
        this.interCasefailCount = interCasefailCount;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public List<CaseLogResultBean> getExecuteLogs() {
        return executeLogs;
    }

    public void setExecuteLogs(List<CaseLogResultBean> executeLogs) {
        this.executeLogs = executeLogs;
    }
}
