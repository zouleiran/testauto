package cn.boss.platform.bean.interfaceManager;

public class JenkinsBean {
    private Integer id;
    private String name;
    private String status;
    private String testenv;//环境
    private String feature;//分支
    private String service;//应用
    private String Cookie;
    private String schedule;
    private String consumer;
    private String git;
    private String source;
    private String time;
    @Override
    public String toString() {
        return "JenkinsBean{" +
                "id=" + id +
                ", name=" + name +
                ", status=" + status +
                ", testenv=" + testenv +
                ", feature=" + feature +
                ", service=" + service +
                ", Cookie=" + Cookie  +
                ", consumer=" + consumer  +
                ", time=" + time  +
                ", schedule=" + schedule  +
                '}';
    }
    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }
    public String getgit() {
        return git;
    }

    public void setgit(String git) {
        this.git = git;
    }
    public String getsource() {
        return source;
    }

    public void setsource(String source) {
        this.source = source;
    }
    public String getschedule() {
        return schedule;
    }

    public void setschedule(String schedule) {
        this.schedule = schedule;
    }
    public String getconsumer() {
        return consumer;
    }

    public void setconsumer(String consumer) {
        this.consumer = consumer;
    }
    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }
    public String gettestenv() {
        return testenv;
    }

    public void settestenv(String testenv) {
        this.testenv = testenv;
    }
    public String getfeature() {
        return feature;
    }

    public void setfeature(String feature) {
        this.feature = feature;
    }
    public String getservice() {
        return service;
    }

    public void setservice(String service) {
        this.service = service;
    }
    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String Cookie) {
        this.Cookie = Cookie;
    }
}
