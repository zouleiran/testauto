package cn.boss.platform.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * Created by admin on 2019/3/21.
 */
public class MockInterfaceForm {

    private Integer id;
    @Range(min = 1, max = 999999, message = "接口编号必须为整数!")
    private Integer interfaceId;
    @NotBlank(message = "接口名不能为空！")
    private String name;
    private String domain;
    @NotBlank(message = "接口地址不能为空！")
    private String url;
    @NotBlank(message = "请求类型不能为空！")
    private String method;
    private String parameters;
    private String status;
    @NotBlank(message = "出参不能为空！")
    private String responseResult;
    private String delay;
    @NotBlank(message = "添加人员不能为空！")
    private String author;

    @Override
    public String toString() {
        return "MockInterfaceForm{" +
                "id=" + id +
                ", interfaceId=" + interfaceId +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", parameters='" + parameters + '\'' +
                ", status='" + status + '\'' +
                ", responseResult='" + responseResult + '\'' +
                ", delay='" + delay + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
