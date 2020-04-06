package cn.boss.platform.web.model;

/**
 * Created by admin on 2019/6/27.
 */
public class TesterKpiModel {

    private String name;
    private String email;
    private int bugCount;
    private int effectiveBugCount;
    private int taskCount;
    private int PoneBug;
    private int PtwoBug;
    private int PthreeBug;
    private int PfourBug;
    private int PfiveBug;
    private int PnoBug;
    private int PinvalidBug;
    //比率
    private long ratio;
    //得分
    private  Double score;
    //换算得分
    private String conversionScore;

    //模块名
    private String cname;

    @Override
    public String toString() {
        return "TesterKpiModel{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bugCount=" + bugCount +
                ", effectiveBugCount=" + effectiveBugCount +
                ", taskCount=" + taskCount +
                ", PoneBug=" + PoneBug +
                ", PtwoBug=" + PtwoBug +
                ", PthreeBug=" + PthreeBug +
                ", PfourBug=" + PfourBug +
                ", PfiveBug=" + PfiveBug +
                ", PnoBug=" + PnoBug +
                ", PinvalidBug=" + PinvalidBug +
                ", ratio=" + ratio +
                ", score=" + score +
                ", conversionScore='" + conversionScore + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getEffectiveBugCount() {
        return effectiveBugCount;
    }

    public void setEffectiveBugCount(int effectiveBugCount) {
        this.effectiveBugCount = effectiveBugCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBugCount() {
        return bugCount;
    }

    public void setBugCount(int bugCount) {
        this.bugCount = bugCount;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getPoneBug() {
        return PoneBug;
    }

    public void setPoneBug(int poneBug) {
        PoneBug = poneBug;
    }

    public int getPtwoBug() {
        return PtwoBug;
    }

    public void setPtwoBug(int ptwoBug) {
        PtwoBug = ptwoBug;
    }

    public int getPthreeBug() {
        return PthreeBug;
    }

    public void setPthreeBug(int pthreeBug) {
        PthreeBug = pthreeBug;
    }

    public int getPfourBug() {
        return PfourBug;
    }

    public void setPfourBug(int pfourBug) {
        PfourBug = pfourBug;
    }

    public int getPfiveBug() {
        return PfiveBug;
    }

    public void setPfiveBug(int pfiveBug) {
        PfiveBug = pfiveBug;
    }

    public int getPnoBug() {
        return PnoBug;
    }

    public void setPnoBug(int pnoBug) {
        PnoBug = pnoBug;
    }

    public int getPinvalidBug() {
        return PinvalidBug;
    }

    public void setPinvalidBug(int pinvalidBug) {
        PinvalidBug = pinvalidBug;
    }

    public long getRatio() {
        return ratio;
    }

    public void setRatio(long ratio) {
        this.ratio = ratio;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getConversionScore() {
        return conversionScore;
    }

    public void setConversionScore(String conversionScore) {
        this.conversionScore = conversionScore;
    }
}