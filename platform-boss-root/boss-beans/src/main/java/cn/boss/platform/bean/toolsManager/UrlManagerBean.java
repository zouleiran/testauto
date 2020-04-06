package cn.boss.platform.bean.toolsManager;

/**
 * Created by admin on 2019/1/28.
 */
public class UrlManagerBean {

    private Integer id;
    private Integer type;
    private String name;
    private String url;
    private String message;

    @Override
    public String toString() {
        return "UrlManagerBean{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
