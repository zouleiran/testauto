package cn.boss.platform.bll.kanzhun;

/**
 * Created by admin on 2018/10/18.
 */
public class CommonParam {

    private String model;
    private int channel;
    private String mac;
    private String network;
    private String uniqid;
    private String version;
    private String screen;
    private String key;

    private String t;
    private Long userid;
    private String ticket;

    @Override
    public String toString() {
        return "CommonParam{" +
                "model='" + model + '\'' +
                ", channel=" + channel +
                ", mac='" + mac + '\'' +
                ", network='" + network + '\'' +
                ", uniqid='" + uniqid + '\'' +
                ", version='" + version + '\'' +
                ", screen='" + screen + '\'' +
                ", key='" + key + '\'' +
                ", t='" + t + '\'' +
                ", userid='" + userid + '\'' +
                ", ticket='" + ticket + '\'' +
                '}';
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getUniqid() {
        return uniqid;
    }

    public void setUniqid(String uniqid) {
        this.uniqid = uniqid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
