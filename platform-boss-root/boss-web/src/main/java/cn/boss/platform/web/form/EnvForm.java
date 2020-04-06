package cn.boss.platform.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * Created by admin on 2018/8/9.
 */
public class EnvForm {

    private Integer id;
    @Range(min=1,max=999999,message = "项目组必须为整数!")
    private Integer item;
    @Range(min=1,max=999999,message = "项目格式错误！")
    private Integer projectId;
    @NotBlank(message = "环境名不能为空！")
    private String env;
    @NotBlank(message = "服务名不能为空！")
    private String service;
    @NotBlank(message = "域名不能为空！")
    private String domain;
    @NotBlank(message = "ip不能为空！")
    private String ip;


    @Override
    public String toString() {
        return "EnvForm{" +
                "id=" + id +
                ", item=" + item +
                ", projectId=" + projectId +
                ", env='" + env + '\'' +
                ", domain='" + domain + '\'' +
                ", ip='" + ip + '\'' +
                ", service='" + service + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
