package cn.boss.platform.bean.interfaceManager;

/**
 * Created by admin on 2018/8/25.
 */
public class CaseLogResultBean {

    private String caseName;
    private CaseExecuteLogBean bean;


    public String getCaseName() {
        return caseName;
    }
    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
    public CaseExecuteLogBean getBean() {
        return bean;
    }
    public void setBean(CaseExecuteLogBean bean) {
        this.bean = bean;
    }

}
