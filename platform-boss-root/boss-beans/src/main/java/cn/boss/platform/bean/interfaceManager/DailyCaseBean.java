package cn.boss.platform.bean.interfaceManager;

/**
 * Created by admin on 2019/2/2.
 */
public class DailyCaseBean {

    private Integer project_id;
    private Integer caseExecuteCount;
    private Integer team;

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getCaseExecuteCount() {
        return caseExecuteCount;
    }

    public void setCaseExecuteCount(Integer caseExecuteCount) {
        this.caseExecuteCount = caseExecuteCount;
    }

    public Integer getTeam() {
        return team;
    }

    public void setTeam(Integer team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "DailyCaseBean{" +
                "project_id=" + project_id +
                ", caseExecuteCount=" + caseExecuteCount +
                ", team=" + team +
                '}';
    }
}
