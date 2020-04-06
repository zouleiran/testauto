package cn.boss.platform.bean.interfaceManager;

import java.util.Date;

/**
 * Created by admin on 2018/7/26.
 */
public class InterfaceBean extends PageBean{

    private Integer id;
    private Integer orderCode;
    private Integer projectId;
    private Integer groupId;
    private String version;
    private String name;
    private Integer status;
    private String method;
    private String http;
    private String path;
    private String params ;
    private String commonParams;
    private String headers;
    private String author;
    private String cookies;
    private String response;
    private String message;
    private Date createTime;
    private Date updateTime;

    //分组名
    private String groupName;
    //接口下用例数
    private int caseCount;
    //用于获取项目组使用
    private String label;

    @Override
    public String toString() {
        return "InterfaceBean{" +
                "id=" + id +
                ", orderCode=" + orderCode +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", version='" + version + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", method='" + method + '\'' +
                ", http='" + http + '\'' +
                ", path='" + path + '\'' +
                ", params='" + params + '\'' +
                ", commonParams='" + commonParams + '\'' +
                ", headers='" + headers + '\'' +
                ", author='" + author + '\'' +
                ", cookies='" + cookies + '\'' +
                ", response='" + response + '\'' +
                ", message='" + message + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", groupName='" + groupName + '\'' +
                ", caseCount=" + caseCount +
                ", label='" + label + '\'' +
                '}';
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCommonParams() {
        return commonParams;
    }

    public void setCommonParams(String commonParams) {
        this.commonParams = commonParams;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(int caseCount) {
        this.caseCount = caseCount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
