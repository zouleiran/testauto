package cn.boss.platform.bean.toolsManager;

import java.util.Date;

/**
 * Created by admin on 2019/4/10.
 */
public class UserInfoBean {

    private Integer id;
    private String phone;
    private String password;
    private String domain;
    private String env;
    private String awt;
    private String type;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", domain='" + domain + '\'' +
                ", env='" + env + '\'' +
                ", awt='" + awt + '\'' +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getAwt() {
        return awt;
    }

    public void setAwt(String awt) {
        this.awt = awt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
