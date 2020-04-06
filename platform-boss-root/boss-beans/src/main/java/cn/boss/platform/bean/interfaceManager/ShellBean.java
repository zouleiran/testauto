package cn.boss.platform.bean.interfaceManager;

public class ShellBean {
    private Integer id;
    private String servername;//应用名称
    private String access;//路径
    private String port;//端口
    private String host;//ip
    private String user;//用户名
    private String password;//密码
    private String shell;//执行的shell命令
    private String env;//标志各个环境
    private String yn;
    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }
    public String getenv() {
        return env;
    }

    public void env(String env) {
        this.env = env;
    }
    public String getshell() {
        return shell;
    }

    public void setshell(String shell) {
        this.shell = shell;
    }
    public String getservername() {
        return servername;
    }

    public void setservername(String servername) {
        this.servername = servername;
    }
    public String getaccess() {
        return access;
    }

    public void setaccess(String access) {
        this.access = access;
    }
    public String getport() {
        return port;
    }

    public void setport(String port) {
        this.port = port;
    }
    public String gethost() {
        return host;
    }

    public void sethost(String host) {
        this.host = host;
    }
    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }
    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }
    public String getyn() {
        return yn;
    }

    public void setyn(String yn) {
        this.yn = yn;
    }
}
