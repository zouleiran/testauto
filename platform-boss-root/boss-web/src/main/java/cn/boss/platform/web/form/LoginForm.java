package cn.boss.platform.web.form;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2019/4/20.
 */
public class LoginForm {

    private String name;
    private String email;
    private String uid;
    private String introduction;
    private String token;
    private String auth;
    private List<String> role = Arrays.asList("admin");


    @Override
    public String toString() {
        return "LoginForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", uid='" + uid + '\'' +
                ", introduction='" + introduction + '\'' +
                ", token='" + token + '\'' +
                ", role=" + role +
                '}';
    }
    public String getauth() {
        return auth;
    }

    public void setauth(String auth) {
        this.auth = auth;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
