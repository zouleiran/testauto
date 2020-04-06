package cn.boss.platform.web.form;

import cn.boss.platform.web.validator.JSON;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * Created by admin on 2018/9/7.
 */
public class BatchCaseStepForm {

    private Integer id;
    @Range(min = 1, max = 999999, message = "项目编号 必须为整数!")
    private Integer projectId;
    @Range(min = 1, max = 999999, message = "分组编号 必须为整数!")
    private Integer groupId;
    @Range(min = 1, max = 999999, message = "环境编号 必须为整数!")
    private Integer envId;
    @Range(min = 1, max = 999999, message = "batch编号 必须为整数!")
    private Integer batchId;
    @Range(min = 1, max = 999999, message = "接口编号 必须为整数!")
    private Integer interfaceId;


    @NotBlank(message = "batch step名 不能为空！")
    private String caseName;
    private String secretKey;
    private String t;
    @JSON(message = "公共参数 必须为json格式!")
    private String publicParameters;
    @JSON(message = "参数 必须为json格式!")
    private String parameters;
    private String headers;
    //前后置处理操作
    @JSON(message = "前置处理 必须为json格式!")
    private String preProcessing;
    @JSON(message = "后置处理 必须为json格式!")
    private String postProcessing;

    private int expectedStatus;
    private String expectedResult;
    @NotBlank(message = "添加人员 不能为空！")
    private String author;

//    private BatchCaseBean batchCaseBean;


    @Override
    public String toString() {
        return "BatchCaseStepForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", envId=" + envId +
                ", batchId=" + batchId +
                ", interfaceId=" + interfaceId +
                ", caseName='" + caseName + '\'' +
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
                '}';
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

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
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
}

