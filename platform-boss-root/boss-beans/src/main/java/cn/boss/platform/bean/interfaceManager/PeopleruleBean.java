package cn.boss.platform.bean.interfaceManager;

public class PeopleruleBean {
    private Integer id;
    private String email;
    private String username;
    private String ruleid;
    private String yn;
    @Override
    public String toString() {
        return "PeopleruleBean{" +
                "id=" + id +
                ", email=" + email +
                ", username=" + username +
                ", ruleid=" + ruleid +
                ", yn='" + yn + '\'';
    }
    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }
    public String getruleid() {
        return ruleid;
    }

    public void setruleid(String ruleid) {
        this.ruleid = ruleid;
    }
    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }
    public String getyn() {
        return yn;
    }

    public void setyn(String yn) {
        this.yn = yn;
    }
    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
}
