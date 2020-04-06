package cn.boss.platform.service.tools;

import cn.boss.platform.bean.interfaceManager.JenkinsBean;
import cn.boss.platform.bll.InterfaceManager.JenkinsBll;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 利用HttpClient进行post请求的工具类
 * @ClassName: HttpClientUtil
 * @Description: TODO
 * @author Devin <xxx> 
 * @date 2017年2月7日 下午1:43:38 
 *
 */
@Service
public class HttpClientUtil {
    @Resource
    private JenkinsBll jenkinsbll;
    @SuppressWarnings("resource")
    public static String doPost(String url,String jsonstr,String charset,String Cookie){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Cookie",Cookie);
//            httpPost.addHeader("Content-Type", "application/json");
//            application/x-www-form-urlencoded
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            StringEntity se = new StringEntity(jsonstr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public static String doposthttp(String url,String param){
        String result="";
        BufferedReader in=null;
        PrintWriter out=null;
        try{
            String urlstring=url;
            URL realurl=new URL(url);
            System.out.println("请求的服务器主机域名："+realurl.getHost().toString());
//打开与此URL的连接
            URLConnection connection=realurl.openConnection();
//设置请求连接时间和读取数据时间
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(7000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-XSRF-TOKEN","N3JDn9yO--bUB1HZkgMJ1BTXaIU7UrQ-or4w");
            connection.setRequestProperty("Cookie","ssid=eyJjc3JmU2VjcmV0IjoibDRPdzNyUU83b3hTV2o4aS1yODZxX0lYIn0=; ssid.sig=x_x8n8fggnYXZUMw4CxzuF2vdRM; XSRF-TOKEN=oI0f6UnZ-AtrQKAsKjfSSLByk3KgfNPgc880");
//post请求的时候必须要设置的两个属性
            connection.setDoOutput(true);
            connection.setDoInput(true);
//获取URLconnextion对象对应的输出流
            out=new PrintWriter(connection.getOutputStream());
//发送参数
            out.print(param);
//输出流的缓冲
            out.flush();
//读取获取的数据
            in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line=in.readLine())!=null){
                result+=line;
            }
        }catch(Exception e){
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }finally {
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch (Exception e2) {
                System.out.println("关闭请求流出现异常！" + e2);
                e2.printStackTrace();
            }
        }
        return result;
    }
    public static JenkinsBean getjenkins(int jenkinsid, String Cookie,String mubiao){
        JenkinsBean a=new JenkinsBean();
        String url="https://ci.kanzhun-inc.com/view/zhipin-service/job/"+mubiao+"/"+jenkinsid+"/parameters/";
        String y=doget1(url,"UTF-8",Cookie);
        System.out.println("zlrllll"+y);
        String schedule=y.substring(y.indexOf("schedule")+"schedule".length());
        System.out.println("zlrllll"+schedule);
        schedule=schedule.substring(0,schedule.indexOf("setting-no-help"));
        System.out.println(schedule);
        if(schedule.indexOf("checked=\"true\"")!=-1)
            a.setschedule("1");
        else
            a.setschedule("0");
        String consumer=y.substring(y.indexOf("consumer")+"consumer".length());
        consumer=consumer.substring(0,consumer.indexOf("setting-no-help"));
//        System.out.println(consumer);
        if(consumer.indexOf("checked=\"true\"")!=-1)
            a.setconsumer("1");
        else
            a.setconsumer("0");


//        if(y.substring(y.indexOf("schedule"),y.indexOf("是否是schedule")).indexOf("checked=\"true\"")!=-1)
//        {
//            //存在schedule
//            a.setschedule("1");
//        }
//        else
//            a.setschedule("0");
//        if(y.substring(y.indexOf("consumer"),y.indexOf("是否是consumer")).indexOf("checked=\"true\"")!=-1)
//        {
//            //存在consumer
//            a.setconsumer("1");
//        }
//        else
//            a.setconsumer("0");
        y=y.substring(y.indexOf("value")+5);
        y=y.substring(y.indexOf("value")+7);
        a.setfeature(y.substring(0,y.indexOf("\"")));
        y=y.substring(y.indexOf("value")+5);
        y=y.substring(y.indexOf("value")+7);
        a.setservice(y.substring(0,y.indexOf("\"")));
        y=y.substring(y.indexOf("value")+5);
        y=y.substring(y.indexOf("value")+7);
        a.settestenv(y.substring(0,y.indexOf("\"")));
        String url2="https://ci.kanzhun-inc.com/view/zhipin-service/job/"+mubiao+"/"+jenkinsid+"/consoleText";
        String s=doget1(url2,"UTF-8",Cookie);
        String q=s.substring(s.indexOf("Started by user ")+10,s.indexOf("Started by user ")+100);
        a.setname(q.substring(q.indexOf("user ")+5,q.indexOf("\n")));
        a.setstatus(s.substring(s.indexOf("Finished: ")+10));
        a.setCookie(Cookie);
        a.setid(jenkinsid);
        String url5="https://ci.kanzhun-inc.com/view/zhipin-service/job/"+mubiao+"/"+jenkinsid;
        s=doget1(url5,"UTF-8",Cookie);
        s=s.substring(s.indexOf("Build #"+jenkinsid)+("Build #"+jenkinsid).length()+10);
        s=s.substring(0,s.indexOf(")"));
        s=s.replaceFirst("PM","下午");
        s=s.replaceFirst("AM","上午");
        s=s.replaceFirst("Jan","一月");
        s=s.replaceFirst("Feb","二月");
        s=s.replaceFirst("Mar","三月");
        s=s.replaceFirst("Apr","四月");
        s=s.replaceFirst("May","五月");
        s=s.replaceFirst("Jun","六月");
        s=s.replaceFirst("Jul","七月");
        s=s.replaceFirst("Aug","八月");
        s=s.replaceFirst("Sept","九月");
        s=s.replaceFirst("Oct","十月");
        s=s.replaceFirst("Nov","十一月");
        s=s.replaceFirst("Dec","十二月");

        a.settime(s);
        a.setgit("");
        a.setsource("1");
        return a;
    }
    @SuppressWarnings("resource")
    public static String doget1(String url,String charset,String Cookie){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);
            httpGet.addHeader("Cookie",Cookie);
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public static String doget1(String url, Map<String, Object> paramerMap, String charset, String Cookie){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try{
            if (paramerMap != null) {
                url=url+"?";
                for (String key : paramerMap.keySet()) {
//                    builder.addParameter(key, paramerMap.get(key));
                    url=url+key+"="+ paramerMap.get(key)+"&";
                }
                url=url.substring(0,url.length()-1);
            }
            httpClient = new SSLClient();

            httpGet = new HttpGet(url);
            httpGet.addHeader("Cookie",Cookie);
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public static JenkinsBean getjenkinsyufa(int jenkinsid, String Cookie){
        JenkinsBean a=new JenkinsBean();
        String url="https://ci.kanzhun-inc.com/view/boss-preon/job/zhipin-service-preon/"+jenkinsid+"/parameters/";
        String y=doget1(url,"UTF-8",Cookie);
        a.setschedule("0");
        if(y.substring(y.indexOf("consumer"),y.indexOf("是否是consumer")).indexOf("checked=\"true\"")!=-1)
        {
            //存在consumer
            a.setconsumer("1");
        }
        else
            a.setconsumer("0");
        y=y.substring(y.indexOf("value")+5);
        y=y.substring(y.indexOf("value")+7);
        a.setservice(y.substring(0,y.indexOf("\"")));
//        System.out.println(y.substring(0,y.indexOf("\"")));
        y=y.substring(y.indexOf("value")+5);
        y=y.substring(y.indexOf("value")+7);
        a.setfeature(y.substring(0,y.indexOf("\"")));
//        System.out.println(y.substring(0,y.indexOf("\"")));
        y=y.substring(y.indexOf("value")+5);
        y=y.substring(y.indexOf("value")+7);
        a.settestenv("yf");
//        a.settestenv(y.substring(0,y.indexOf("\"")));
//        System.out.println(y.substring(0,y.indexOf("\"")));
        String url2="https://ci.kanzhun-inc.com/view/boss-preon/job/zhipin-service-preon/"+jenkinsid+"/consoleText";
        String s=doget1(url2,"UTF-8",Cookie);
        String q=s.substring(s.indexOf("Started by user ")+10,s.indexOf("Started by user ")+100);
//        System.out.println(q.substring(q.indexOf("user ")+5,q.indexOf("\n")));
        a.setname(q.substring(q.indexOf("user ")+5,q.indexOf("\n")));
        a.setstatus(s.substring(s.indexOf("Finished: ")+10));
//        System.out.println(s.substring(s.indexOf("Finished: ")+10));
        a.setCookie(Cookie);
        a.setid(jenkinsid);
        String url5="https://ci.kanzhun-inc.com/view/boss-preon/job/zhipin-service-preon/"+jenkinsid;
        s=doget1(url5,"UTF-8",Cookie);
//        System.out.println(s);
        s=s.substring(s.indexOf("Build #"+jenkinsid)+12);
        s=s.substring(9,s.indexOf(")"));
        s=s.replaceFirst("PM","下午");
        s=s.replaceFirst("AM","上午");
        s=s.replaceFirst("Jan","一月");
        s=s.replaceFirst("Feb","二月");
        s=s.replaceFirst("Mar","三月");
        s=s.replaceFirst("Apr","四月");
        s=s.replaceFirst("May","五月");
        s=s.replaceFirst("Jun","六月");
        s=s.replaceFirst("Jul","七月");
        s=s.replaceFirst("Aug","八月");
        s=s.replaceFirst("Sept","九月");
        s=s.replaceFirst("Oct","十月");
        s=s.replaceFirst("Nov","十一月");
        s=s.replaceFirst("Dec","十二月");
//        System.out.println(s);
        a.settime(s);
        a.setsource("1");
        a.setgit("");
        return a;
    }

}