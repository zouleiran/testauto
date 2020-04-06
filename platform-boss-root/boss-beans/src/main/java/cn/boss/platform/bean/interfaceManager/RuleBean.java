package cn.boss.platform.bean.interfaceManager;

public class RuleBean {
    private Integer id;
    private String webid;
    private String ruledes;
    private String yn;
    @Override
    public String toString() {
        return "RuleBean{" +
                "id=" + id +
                ", webid=" + webid +
                ", ruledes=" + ruledes +
                ", yn='" + yn + '\'';
    }
    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }
    public String getwebid() {
        return webid;
    }

    public void setwebid(String webid) {
        this.webid = webid;
    }
    public String getruledes() {
        return ruledes;
    }

    public void setruledes(String ruledes) {
        this.ruledes = ruledes;
    }
    public String getyn() {
        return yn;
    }

    public void setyn(String yn) {
        this.yn = yn;
    }
}
