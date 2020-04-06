package cn.boss.platform.bean.dubboManager;

import java.util.Date;

/**
 * Created by admin on 2018/12/20.
 */
public class ZhipinDubboCaseBean {

    private int id;
    private int interfaceId;
    private int envId;
    private String caseName;
    private int type;
    private String parameters;
    private String expectedResult;
    private String author;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "ZhipinDubboCaseBean{" +
                "id=" + id +
                ", interfaceId=" + interfaceId +
                ", envId=" + envId +
                ", caseName='" + caseName + '\'' +
                ", type=" + type +
                ", parameters='" + parameters + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(int interfaceId) {
        this.interfaceId = interfaceId;
    }

    public int getEnvId() {
        return envId;
    }

    public void setEnvId(int envId) {
        this.envId = envId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
