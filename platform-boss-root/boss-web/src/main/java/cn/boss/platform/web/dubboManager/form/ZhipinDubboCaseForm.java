package cn.boss.platform.web.dubboManager.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * Created by admin on 2018/12/20.
 */
public class ZhipinDubboCaseForm {

    private int id;
    @Range(min=1,max=999999,message = "接口编号 必须为整数!")
    private int interfaceId;
    @Range(min=1,max=999999,message = "环境编号 必须为整数!")
    private int envId;
    @NotBlank(message = "用例名 不能为空！")
    private String caseName;
    private int type;
    private String parameters;
    private String expectedResult;
    private String author;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "ZhipinDubboCaseForm{" +
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

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
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
