package cn.boss.platform.web.model;

import java.util.List;

/**
 * Created by admin on 2019/2/2.
 */
public class DailyCaseModel {

    String data;
    List<String> team;
    List<Integer> caseCount;


    @Override
    public String toString() {
        return "DailyCaseModel{" +
                "data='" + data + '\'' +
                ", team=" + team +
                ", caseCount=" + caseCount +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getTeam() {
        return team;
    }

    public void setTeam(List<String> team) {
        this.team = team;
    }

    public List<Integer> getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(List<Integer> caseCount) {
        this.caseCount = caseCount;
    }
}
