package cn.boss.platform.service.interfaceManager;

import cn.boss.platform.bean.interfaceManager.JenkinsBean;
import cn.boss.platform.bll.InterfaceManager.JenkinsBll;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static cn.boss.platform.service.tools.HttpClientUtil.doget1;

@Service
public class JenkinsServer {
    @Resource
    private JenkinsBll jenkinsbll;
//    public List<JenkinsBean> dojenkins(String testenv,String service){
//        //该部分代码分为两部分，1为更新，2为返回
//        //前端会给定一个环境，如qa，需要更新当前库里面没有的数据并返回qa环境下各个应用数据库中最新的部署情况
//        //jenkins只会保存10个结果
//        //第一步，获取当前最新部署完成的jenkinsid
//        try {
//
//            JenkinsBean a = jenkinsbll.getlastjenkins();
//            String Cookie = a.getCookie();
//            String url = "https://ci.kanzhun-inc.com/view/zhipin-service/job/zhipin-service/lastStableBuild/";
//
//            String x = doget1(url, "UTF-8", Cookie);
//            x = x.substring(x.indexOf("zhipin-service #") + 16);
//            x = x.substring(0, x.indexOf("[Jenkins]") - 1);
//            int jenkinsid = Integer.parseInt(x);
//            //第二步根据jenkinsid看是否数据库中已经是最新的了
//            int aid=a.getid();
//            if(aid<jenkinsid)
//            {
//                int i=0;
//                while (i<10&&aid<jenkinsid)
//                {
//                    jenkinsbll.insertjenkins(getjenkins(jenkinsid,Cookie));
//                    jenkinsid--;
//                    i++;
//                }
//            }
//            //此时已经把所有都更新到最新了,返回当前最新结果数据集合
//            return jenkinsbll.selectJenkins2(testenv,service);
//
//        } catch (NumberFormatException e) {
//            System.out.println("Cookie失效");
//            return null;
//        }
//    }
    public List<JenkinsBean> dojenkins2(String testenv, String service, int begin, int size,String source){
        //该部分代码分为两部分，1为更新，2为返回
        //前端会给定一个环境，如qa，需要更新当前库里面没有的数据并返回qa环境下各个应用数据库中最新的部署情况
        //jenkins只会保存10个结果
        //第一步，获取当前最新部署完成的jenkinsid
        try {

//            JenkinsBean a = jenkinsbll.getlastjenkins();
//            String Cookie = a.getCookie();
//            String url = "https://ci.kanzhun-inc.com/view/zhipin-service/job/zhipin-service/lastStableBuild/";
//
//            String x = doget1(url, "UTF-8", Cookie);
//            x = x.substring(x.indexOf("zhipin-service #") + 16);
//            x = x.substring(0, x.indexOf("[Jenkins]") - 1);
//            int jenkinsid = Integer.parseInt(x);
//            //第二步根据jenkinsid看是否数据库中已经是最新的了
//            int aid=a.getid();
//            if(aid<jenkinsid)
//            {
//                int i=0;
//                while (i<10&&aid<jenkinsid)
//                {
//                    jenkinsbll.insertjenkins(getjenkins(jenkinsid,Cookie));
//                    jenkinsid--;
//                    i++;
//                }
//            }
            //此时已经把所有都更新到最新了,返回当前最新结果数据集合
            return jenkinsbll.selectJenkins2(testenv,service,begin,size,source);

        } catch (NumberFormatException e) {
            System.out.println("Cookie失效");
            return null;
        }
    }

    public List<JenkinsBean> getlastJenkins(String testenv,String source){
        //该部分代码可以获取各个部分最新jenkins分支
        try {
            //此时已经把所有都更新到最新了,返回当前最新结果数据集合
            return jenkinsbll.getlastJenkins(testenv,source);

        } catch (NumberFormatException e) {
            System.out.println("Cookie失效");
            return null;
        }
    }
    //该方法获取jenkinsid所对应的相关结果，并入库等操作

    public void setCookie(String cookie) {
        jenkinsbll.setCookie(cookie);
    }
//    @Test
//    public void test3() {
//        String Cookie="screenResolution=2560x1440; jenkins-timestamper-offset=-28800000; screenResolution=2560x1440; JSESSIONID=6EDD7148852B0D58B86A63A60C5A421D; ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE=em91bGVpcmFuQGthbnpodW4uY29tOjE1NjA5MjY3Mjg3MTc6ZWNlY2ZhNGY5YzVmNTgwYTk0NzU4NDIwY2IxZGE1NmI2OTQ2ZWQzNzZhNTNkN2EzZWYwODY0ZTVhMmYzMGJlMw==";
//        jenkinsbll.insertjenkins(getjenkins(12987,Cookie));
//    }
    @Test
    public void test2() {
        String url1 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_tomcat-qa/lastStableBuild/";

//        String result = doget1(url, "UTF-8", Cookie);
//        result = result.substring(result.indexOf(mubiao+" #") + (mubiao+" #").length());
//        result = result.substring(0, result.indexOf("[Jenkins]") - 1);
//        int jenkinsid = Integer.parseInt(result);
        String Cookie="crowd.token_key=gHSwv3SSh0ZDgEc2BZLttg00; JSESSIONID=609BD3B815A602753E2E37285B13EA6D; ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE=em91bGVpcmFuQGthbnpodW4uY29tOjE1ODI3ODk2NDIyMDY6NTg4NjVhNzkwYTY5NzE3MmQ5ZjVhYjExZjA2YWMyYTkwNjZjYTFjYjYwODhhN2JhYjZiYzhhZDg5YmQzODE4OQ==; screenResolution=1440x900";
//        String url="https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_tomcat-qa/9251/parameters/";
//        String x=doget1(url,"UTF-8",Cookie);
//        System.out.println(x);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        String a = x.substring(0,x.indexOf("\""));
//        System.out.println(a);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        String b = x.substring(0,x.indexOf("\""));
//        System.out.println(b);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        String c = x.substring(0,x.indexOf("\""));
//        System.out.println(c);
        String url3 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_tomcat-qa/9260/consoleText";
        String s = doget1(url3, "UTF-8", Cookie);
        String q = s.substring(s.indexOf("Started by user ") + 10, s.indexOf("Started by user ") + 100);
        System.out.println(q.substring(q.indexOf("user ") + 5, q.indexOf("\n")));
        System.out.println(s.substring(s.indexOf("Finished: ") + 10));
        String url5 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_tomcat-qa/9260";
        s = doget1(url5, "UTF-8", Cookie);
        s = s.substring(s.indexOf("Build #" + "9260") + ("Build #" + "9260").length());
        s = s.substring(10, s.indexOf(")"));
        s = s.replaceFirst("PM", "下午");
        s = s.replaceFirst("AM", "上午");
        s = s.replaceFirst("Jan", "一月");
        s = s.replaceFirst("Feb", "二月");
        s = s.replaceFirst("Mar", "三月");
        s = s.replaceFirst("Apr", "四月");
        s = s.replaceFirst("May", "五月");
        s = s.replaceFirst("Jun", "六月");
        s = s.replaceFirst("Jul", "七月");
        s = s.replaceFirst("Aug", "八月");
        s = s.replaceFirst("Sept", "九月");
        s = s.replaceFirst("Oct", "十月");
        s = s.replaceFirst("Nov", "十一月");
        s = s.replaceFirst("Dec", "十二月");
        System.out.println(s);
//        String y=doget1(url1,"UTF-8",Cookie);
//        System.out.println(y);
//        y = y.substring(y.indexOf("blue_tomcat-qa #") + "blue_tomcat-qa #".length());
//        y = y.substring(0, y.indexOf("[Jenkins]") - 1);
//        int jenkinsid = Integer.parseInt(y);
//        System.out.println(jenkinsid);
    }
    @Test
    public void test3() {
//        String Cookie="crowd.token_key=gHSwv3SSh0ZDgEc2BZLttg00; JSESSIONID=609BD3B815A602753E2E37285B13EA6D; ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE=em91bGVpcmFuQGthbnpodW4uY29tOjE1ODI3ODk2NDIyMDY6NTg4NjVhNzkwYTY5NzE3MmQ5ZjVhYjExZjA2YWMyYTkwNjZjYTFjYjYwODhhN2JhYjZiYzhhZDg5YmQzODE4OQ==; screenResolution=1440x900";
        String Cookie="screenResolution=2560x1440; screenResolution=2560x1440; crowd.token_key=BVS0qN3NGOxyZqhf9MggnA00; JSESSIONID=91E7AE73FA6CC3FA45E3D53241D77159";
//        String url="https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_daemontools-qa/2238/parameters/";
//        String x=doget1(url,"UTF-8",Cookie);
//        System.out.println(x);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        String a = x.substring(0,x.indexOf("\""));
//        System.out.println(a);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        String b = x.substring(0,x.indexOf("\""));
//        System.out.println(b);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        String c = x.substring(0,x.indexOf("\""));
//        System.out.println(c);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        x = x.substring(x.indexOf("value") + "value".length()+2);
//        String d = x.substring(0,x.indexOf("\""));
//        System.out.println(d);

        String url3 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_daemontools-qa/"+2238+"/consoleText";
        String s = doget1(url3, "UTF-8", Cookie);
        String q = s.substring(s.indexOf("Started by user ") + 10, s.indexOf("Started by user ") + 100);
        System.out.println(q.substring(q.indexOf("user ") + 5, q.indexOf("\n")));
        System.out.println(s.substring(s.indexOf("Finished: ") + 10));
        String url5 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_daemontools-qa/"+2238;
        s = doget1(url5, "UTF-8", Cookie);
        s = s.substring(s.indexOf("Build #" + 2238) + ("Build #" + 2238).length());
        s = s.substring(10, s.indexOf(")"));
        s = s.replaceFirst("PM", "下午");
        s = s.replaceFirst("AM", "上午");
        s = s.replaceFirst("Jan", "一月");
        s = s.replaceFirst("Feb", "二月");
        s = s.replaceFirst("Mar", "三月");
        s = s.replaceFirst("Apr", "四月");
        s = s.replaceFirst("May", "五月");
        s = s.replaceFirst("Jun", "六月");
        s = s.replaceFirst("Jul", "七月");
        s = s.replaceFirst("Aug", "八月");
        s = s.replaceFirst("Sept", "九月");
        s = s.replaceFirst("Oct", "十月");
        s = s.replaceFirst("Nov", "十一月");
        s = s.replaceFirst("Dec", "十二月");
        System.out.println(s);
    }
}
