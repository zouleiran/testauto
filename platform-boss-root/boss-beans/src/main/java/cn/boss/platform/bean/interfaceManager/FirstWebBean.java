package cn.boss.platform.bean.interfaceManager;

import java.util.List;

public class FirstWebBean {
    private Integer firstid;
    private String firstdes;
    private List<RuleBean> RuleBean;
    public Integer getfirstid() {
        return firstid;
    }

    public void setfirstid(Integer firstid) {
        this.firstid = firstid;
    }
    public String getfirstdes() {
        return firstdes;
    }

    public void setfirstdes(String firstdes) {
        this.firstdes = firstdes;
    }
    public List<cn.boss.platform.bean.interfaceManager.RuleBean> getRuleBean() {
        return RuleBean;
    }

    public void setRuleBean(List<cn.boss.platform.bean.interfaceManager.RuleBean> RuleBean) {
        this.RuleBean = RuleBean;
    }
}
