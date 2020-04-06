package cn.boss.platform.bean.interfaceManager;

public class WebBean {
    private Integer id;
    private String weburl;
    private String webdes;
    private Integer parent_id;
    private String yn;
    @Override
    public String toString() {
        return "RuleBean{" +
                "id=" + id +
                ", weburl=" + weburl +
                ", webdes=" + webdes +
                ", yn='" + yn + '\'';
    }
    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }
    public String getweburl() {
        return weburl;
    }

    public void setweburl(String weburl) {
        this.weburl = weburl;
    }
    public String getwebdes() {
        return webdes;
    }

    public void setparent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }
    public Integer getparent_id() {
        return parent_id;
    }

    public void setwebdes(String webdes) {
        this.webdes = webdes;
    }
    public String getyn() {
        return yn;
    }

    public void setyn(String yn) {
        this.yn = yn;
    }
}
