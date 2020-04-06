package cn.boss.platform.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * Created by admin on 2018/7/26.
 */
public class InterfaceForm {

    private Integer id;
    private Integer orderCode;
    @Range(min = 1, max = 999999, message = "项目编号必须为整数!")
    private Integer projectId;
    @Range(min = 1, max = 999999, message = "分组编号必须为整数!")
    private Integer groupId;
    private String version;
    @NotBlank(message = "接口名不能为空！")
    private String name;
    private Integer status;
    @NotBlank(message = "请求类型不能为空!")
    private String method;
    private String http;
    @NotBlank(message = "接口路径不能为空!")
    private String path;
    private String params;
    private String commonParams;
    private String headers;
    private String cookies;
    private String author;
    private String response;
    private String message;

    private int pageIndex;
    private int pageSize;

    @Override
    public String toString() {
        return "InterfaceForm{" +
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
                ", cookies='" + cookies + '\'' +
                ", author='" + author + '\'' +
                ", response='" + response + '\'' +
                ", message='" + message + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
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

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}


