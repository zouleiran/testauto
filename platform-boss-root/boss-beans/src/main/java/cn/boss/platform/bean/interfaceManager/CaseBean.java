package cn.boss.platform.bean.interfaceManager;

import java.util.Date;

/**
 * Created by admin on 2018/8/11.
 */
public class CaseBean {

    private Integer id;
    private Integer projectId;
    private Integer groupId;
    private Integer interfaceId;
    private Integer envId;

    private String caseName;
    private String type;
    private String secretKey;
    private String t;
    private String publicParameters;
    private String parameters;
    private String headers;
    //前后置处理操作
    private String preProcessing;
    private String postProcessing;

    private int expectedStatus;
    private String expectedResult;
    private String author;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    private InterfaceBean interfaceBean;

    @Override
    public String toString() {
        return "CaseBean{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", interfaceId=" + interfaceId +
                ", envId=" + envId +
                ", caseName='" + caseName + '\'' +
                ", type='" + type + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", t='" + t + '\'' +
                ", publicParameters='" + publicParameters + '\'' +
                ", parameters='" + parameters + '\'' +
                ", headers='" + headers + '\'' +
                ", preProcessing='" + preProcessing + '\'' +
                ", postProcessing='" + postProcessing + '\'' +
                ", expectedStatus=" + expectedStatus +
                ", expectedResult='" + expectedResult + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", interfaceBean=" + interfaceBean +
                '}';
    }


    public InterfaceBean getInterfaceBean() {
        return interfaceBean;
    }

    public void setInterfaceBean(InterfaceBean interfaceBean) {
        this.interfaceBean = interfaceBean;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getPublicParameters() {
        return publicParameters;
    }

    public void setPublicParameters(String publicParameters) {
        this.publicParameters = publicParameters;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getPreProcessing() {
        return preProcessing;
    }

    public void setPreProcessing(String preProcessing) {
        this.preProcessing = preProcessing;
    }

    public String getPostProcessing() {
        return postProcessing;
    }

    public void setPostProcessing(String postProcessing) {
        this.postProcessing = postProcessing;
    }

    public int getExpectedStatus() {
        return expectedStatus;
    }

    public void setExpectedStatus(int expectedStatus) {
        this.expectedStatus = expectedStatus;
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



