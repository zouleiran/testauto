package cn.boss.platform.web.dubboManager.form;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by admin on 2018/12/19.
 */
public class ZhipinDubboInterfaceForm {

    private int id;
    @NotBlank(message = "服务名 不能为空！")
    private String serverName;
    @NotBlank(message = "接口名 不能为空！")
    private String interfaceName;
    @NotBlank(message = "方法名 不能为空！")
    private String methodName;
    private String name;
    private String message;
    private int status;
    private String author;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "ZhipinDubboInterfaceForm{" +
                "id=" + id +
                ", serverName='" + serverName + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
