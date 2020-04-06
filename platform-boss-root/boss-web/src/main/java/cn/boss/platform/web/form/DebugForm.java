package cn.boss.platform.web.form;

import java.util.Map;

/**
 * Created by caosenquan on 2017/12/8.
 */
public class DebugForm {

    String url;
    int status;
    String secretKey;
    String response;

    Map responseMap;
    String headers;
    Map Cookies;
    String message;

    @Override
    public String toString() {
        return "DebugForm{" +
                "url='" + url + '\'' +
                ", status=" + status +
                ", signKey='" + secretKey + '\'' +
                ", response='" + response + '\'' +
                ", responseMap=" + responseMap +
                ", headers='" + headers + '\'' +
                ", Cookies=" + Cookies +
                ", message='" + message + '\'' +
                '}';
    }

    public Map getResponseMap() {
        return responseMap;
    }

    public void setResponseMap(Map responseMap) {
        this.responseMap = responseMap;
    }

    public Map getCookies() {
        return Cookies;
    }

    public void setCookies(Map cookies) {
        Cookies = cookies;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public DebugForm() {
    }

    public void setDebug(String url, int status,String response) {
        this.url = url;
        this.status = status;
        this.response = response;
    }


    public void setDebug(String url, int status, String secretKey,String response,Map cookies,String message) {
        this.url = url;
        this.status = status;
        this.secretKey = secretKey;
        this.response = response;
        this.message = message;
        this.Cookies = cookies;
    }


    public void setHeadersDebug(String url,int status, Map response,String headers, Map cookies, String message) {
        this.url = url;
        this.status = status;
        this.headers = headers;
        this.responseMap = response;
        this.message = message;
        this.Cookies = cookies;
    }

    public void setAllDebug(String url, int status, String secretKey,String response,String headers, Map cookies,String message) {
        this.url = url;
        this.status = status;
        this.secretKey = secretKey;
        this.headers = headers;
        this.Cookies = cookies;
        this.response = response;
        this.message = message;
    }

    public void setAllDebug(String url, int status, String secretKey,Map responseMap,String headers, Map cookies,String message) {
        this.url = url;
        this.status = status;
        this.secretKey = secretKey;
        this.headers = headers;
        this.Cookies = cookies;
        this.responseMap = responseMap;
        this.message = message;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
