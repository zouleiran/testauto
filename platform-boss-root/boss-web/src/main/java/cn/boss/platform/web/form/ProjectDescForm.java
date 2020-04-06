package cn.boss.platform.web.form;

import java.util.Date;

/**项目描述
 * Created by admin on 2018/9/3.
 */
public class ProjectDescForm {

    //接口数
    private Integer interfaceCount;
    //用例数
    private Integer caseCount;
    //更新时间
    private Date updateTime;

    @Override
    public String toString() {
        return "ProjectDescForm{" +
                "interfaceCount=" + interfaceCount +
                ", caseCount=" + caseCount +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(Integer interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    public Integer getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(Integer caseCount) {
        this.caseCount = caseCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
