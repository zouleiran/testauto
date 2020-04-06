package cn.boss.platform.bean.dubboManager;

/**
 * Created by admin on 2018/12/10.
 */
public class ZhipinEnvBean {

    private Integer id;
    private String zhipinName;
    private String env;
    private String person;
    private String address;
    private String healthUrl;
    private String healthAddress;

    @Override
    public String toString() {
        return "ZhipinEnvBean{" +
                "id=" + id +
                ", zhipinName='" + zhipinName + '\'' +
                ", env='" + env + '\'' +
                ", person='" + person + '\'' +
                ", address='" + address + '\'' +
                ", healthUrl='" + healthUrl + '\'' +
                ", healthAddress='" + healthAddress + '\'' +
                '}';
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getHealthUrl() {
        return healthUrl;
    }

    public void setHealthUrl(String healthUrl) {
        this.healthUrl = healthUrl;
    }

    public String getHealthAddress() {
        return healthAddress;
    }

    public void setHealthAddress(String healthAddress) {
        this.healthAddress = healthAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZhipinName() {
        return zhipinName;
    }

    public void setZhipinName(String zhipinName) {
        this.zhipinName = zhipinName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
