package cn.boss.platform.service.interfaceManager;

import cn.boss.platform.bean.interfaceManager.CaseBean;
import cn.boss.platform.bean.interfaceManager.JenkinsBean;
import cn.boss.platform.bean.interfaceManager.TaskBean;
import cn.boss.platform.bll.InterfaceManager.CaseBll;
import cn.boss.platform.bll.InterfaceManager.InterfaceBll;
import cn.boss.platform.bll.InterfaceManager.JenkinsBll;
import cn.boss.platform.bll.InterfaceManager.TaskBll;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static cn.boss.platform.service.tools.HttpClientUtil.*;

@Service
public class QuartzJobFactory implements Job {
    @Autowired
    private ExecuteService executeService;
    //    public final Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private CaseBll caseBll;
    @Resource
    private JenkinsBll jenkinsbll;
    @Autowired
    private InterfaceBll interfaceBll;
    @Autowired
    private TaskBll taskBll;
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap a = context.getMergedJobDataMap();
        Set<String> b = a.keySet();
        for(String taskid:b) {

            System.out.println("任务号是"+a.get(taskid));
            String x=(String)a.get(taskid);
            if(x.equals("72")) {
//                https://ci.kanzhun-inc.com/view/zhipin-service/job/zhipin-pay-service/
                JenkinsBean v = jenkinsbll.getlastjenkins();
                JenkinsBean vpay = jenkinsbll.getlastjenkinspay();
                String Cookie = v.getCookie();
//                这里是常规的jenkins服务化爬取
                getweifuwujenkins(Cookie,v.getid(),"zhipin-service");
                //                这里是pay服务化爬取
                getweifuwujenkins(Cookie,vpay.getid(),"zhipin-pay-service");

//                这里是进行相关admin分支爬取
                getadminjenkins(Cookie);
//                这里是进行相关预发环境微服务爬取
                getyfjenkins(Cookie);
//                这里是进行店长相关jenkins服务爬取
                getdianzhangweifuwu(Cookie);
//                这里是进行店长相关admin服务爬取
                getdianzhangadmin(Cookie);
                return;
            }
            else {
                int x1 = Integer.parseInt(x);
                List<TaskBean> d = taskBll.searchTaskById(x1);
                TaskBean TaskBean = d.get(0);
                try {
                    int[] arrayCase = null;
                    int[] arrayInter = null;
                    if (!StringUtils.isBlank(TaskBean.getInterfaceId())) {
                        String[] split = TaskBean.getInterfaceId().split(",");
                        arrayInter = Arrays.stream(split).mapToInt(Integer::valueOf).toArray();
                    }
                    List<CaseBean> list = caseBll.getExecute(TaskBean.getProjectId(), TaskBean.getGroupId(), TaskBean.getEnvId(), TaskBean.getVersion(), arrayInter, arrayCase, TaskBean.getAuthor());
                    if (list.isEmpty()) {
                        System.out.println("无可执行的用例！");
                        return;
                    }
                    for (CaseBean c : list) {
                        c.setInterfaceBean(interfaceBll.getInterfaceById(c.getInterfaceId()));
                    }
                    int serialId = (int) System.currentTimeMillis() / 1000;
                    executeService.todo(list, serialId, Integer.parseInt(x), TaskBean.getAuthor(),null);
                    System.out.println("执行完毕，总共执行 " + list.size() + " 条用例！");
                    return;
                } catch (Exception e) {
                    System.out.println("用例执行异常!");
                    return;
                }
            }
        }
    }


    private void getadminjenkins(String cookie) {
        //qa1 新老admin
        getjenkinsadmin(cookie,"qa","newadmin");
        getjenkinsadmin(cookie,"qa","oldservice");
        //qa2的新老admin分支爬取
        getjenkinsadmin(cookie,"qa2","newadmin");
        getjenkinsadmin(cookie,"qa2","oldservice");
        //qa3的新老admin分支爬取
        getjenkinsadmin(cookie,"qa3","newadmin");
        getjenkinsadmin(cookie,"qa3","oldservice");
        //rd的新老admin分支爬取
        getjenkinsadmin(cookie,"rd","newadmin");
        getjenkinsadmin(cookie,"rd","oldservice");
        //yf的新老admin分支爬取
        getjenkinsadmin(cookie,"yf","newadmin");
        getjenkinsadmin(cookie,"yf","oldservice");
    }
    public void getjenkinsadmin(String Cookie,String testenv,String service){
        //先对数据进行初始化，jenkins路径与我的命名有差异
        String testenv2=null;
        String service2=null;
        if(testenv.equals("qa")) {
            testenv2 = "boss-qa";
            if(service.equals("oldservice"))
            {
                service2="techwolf-boss-qa";
            }
            else
                service2="boss-new-admin-qa";
        }
        else if(testenv.equals("qa2")) {
            testenv2 = "boss-qa2";
            if(service.equals("oldservice"))
            {
                service2="techwolf-boss-qa2";
            }
            else
                service2="techwolf-boss-new-admin-qa2";
        }
        else if(testenv.equals("qa3")) {
            testenv2 = "boss-qa3";
            if(service.equals("oldservice"))
            {
                service2="techwolf-boss-qa3";
            }
            else
                service2="techwolf-boss-newadmin-qa3";
        }
        else if(testenv.equals("rd")){
            testenv2 = "boss-dev";
            if(service.equals("oldservice"))
            {
                service2="techwolf-boss-dev";
            }
            else
                service2="boss-new-admin";
        }
        else if(testenv.equals("yf")){
            testenv2 = "boss-preon";
            if(service.equals("oldservice"))
            {
                service2="techwolf-boss-preonline";
            }
            else
                service2="boss-new-admin-preon";
        }
        //获取最新部署的数量
        String url="https://ci.kanzhun-inc.com/view/"+testenv2+"/job/"+service2+"/lastBuild/";
        String x=doget1(url,"UTF-8",Cookie);
        x=x.substring(x.indexOf(service2+" #")+(service2+" #").length());
        x=x.substring(0,x.indexOf("[Jenkins]")-1);
        int jenkinsid= Integer.parseInt(x);
        //判断是否需要更新数据
        JenkinsBean a = new JenkinsBean();
        int id=jenkinsbll.getlastjenkinsadmin(testenv,service);
        if(id<jenkinsid) {
            //需要更新？
            String url2 = "https://ci.kanzhun-inc.com/view/" + testenv2 + "/job/" + service2 + "/" + jenkinsid + "/parameters/";
//            System.out.println("urlzlrzlr"+url2);
            String y = doget1(url2, "UTF-8", Cookie);
//            System.out.println("urlyyy"+y);
            y = y.substring(y.indexOf("value") + 7);
            y = y.substring(y.indexOf("value") + 7);
            String m=y;
            m = m.substring(m.indexOf("setting-input") + "setting-input".length()+5);
//            boss-preon/job/boss-new-admin-preon/
            if(testenv2.equals("boss-preon")&&service2.equals("boss-new-admin-preon"))
                m = m.substring(7,m.indexOf("/></td><td class=\"setting-no-help\"")-2);
            else
                m = m.substring(0,m.indexOf("textarea")-2);
            y = y.substring(0, y.indexOf("\""));
            a.setid(Integer.valueOf(jenkinsid));
            a.settestenv(testenv);
            a.setservice(service);
            a.setCookie(Cookie);
            a.setschedule("0");
            a.setgit("");
            a.setsource("1");
            a.setconsumer("0");
            a.setfeature(y+"("+m+")");
            String url3 = "https://ci.kanzhun-inc.com/view/" + testenv2 + "/job/" + service2 + "/" + jenkinsid + "/consoleText";
            String s = doget1(url3, "UTF-8", Cookie);
            String q = s.substring(s.indexOf("Started by user ") + 10, s.indexOf("Started by user ") + 100);
            a.setname(q.substring(q.indexOf("user ") + 5, q.indexOf("\n")));
            a.setstatus(s.substring(s.indexOf("Finished: ") + 10));
            String url5 = "https://ci.kanzhun-inc.com/view/" + testenv2 + "/job/" + service2 + "/" + jenkinsid;
            s = doget1(url5, "UTF-8", Cookie);
            s = s.substring(s.indexOf("Build #" + jenkinsid) + ("Build #" + jenkinsid).length());
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
            a.settime(s);
            a.setgit("");
            a.setsource("1");
            jenkinsbll.insertjenkins(a);
        }
    }
    private void getdianzhangadmin(String Cookie) {
//        String Cookie="crowd.token_key=gHSwv3SSh0ZDgEc2BZLttg00; JSESSIONID=609BD3B815A602753E2E37285B13EA6D; ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE=em91bGVpcmFuQGthbnpodW4uY29tOjE1ODI3ODk2NDIyMDY6NTg4NjVhNzkwYTY5NzE3MmQ5ZjVhYjExZjA2YWMyYTkwNjZjYTFjYjYwODhhN2JhYjZiYzhhZDg5YmQzODE4OQ==; screenResolution=1440x900";
        String url1 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_tomcat-qa/lastStableBuild/";
        String y=doget1(url1,"UTF-8",Cookie);
        y = y.substring(y.indexOf("blue_tomcat-qa #") + "blue_tomcat-qa #".length());
        y = y.substring(0, y.indexOf("[Jenkins]") - 1);
        int id=jenkinsbll.getlastjenkinsdianzhangadmin().getid();
        int jenkinsid = Integer.parseInt(y);
        int i = 0;
        while (i < 5 && id < jenkinsid) {
            jenkinsbll.insertjenkins(getlastjenkinsdianzhangadmin(jenkinsid, Cookie));
            jenkinsid--;
            i++;
        }
    }

    private JenkinsBean getlastjenkinsdianzhangadmin(int jenkinsid, String Cookie) {
        String url="https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_tomcat-qa/"+jenkinsid+"/parameters/";
        String x=doget1(url,"UTF-8",Cookie);
        JenkinsBean result=new JenkinsBean();
//        System.out.println(x);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        String feature = x.substring(0,x.indexOf("\""));
//        System.out.println(feature);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        String service = x.substring(0,x.indexOf("\""));
//        System.out.println(service);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        String testenv = x.substring(0,x.indexOf("\""));
        result.setid(Integer.valueOf(jenkinsid));
        result.settestenv(testenv);
        result.setservice("dzadmin");
        result.setCookie(Cookie);
        result.setschedule("0");
        result.setgit("");
        result.setsource("2");
        result.setconsumer("0");
        result.setfeature(service+"("+feature+")");
        String url3 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_tomcat-qa/"+jenkinsid+"/consoleText";
        String s = doget1(url3, "UTF-8", Cookie);
        String q = s.substring(s.indexOf("Started by user ") + 10, s.indexOf("Started by user ") + 100);
        result.setname(q.substring(q.indexOf("user ") + 5, q.indexOf("\n")));
        result.setstatus(s.substring(s.indexOf("Finished: ") + 10));
        String url5 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_tomcat-qa/"+jenkinsid;
        s = doget1(url5, "UTF-8", Cookie);
        s = s.substring(s.indexOf("Build #" + jenkinsid) + ("Build #" + jenkinsid).length());
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
        result.settime(s);
        return result;
    }

    private void getdianzhangweifuwu(String Cookie) {
        String url = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_daemontools-qa/lastStableBuild/";
        String result = doget1(url, "UTF-8", Cookie);
        result = result.substring(result.indexOf("blue_daemontools-qa #") + "blue_daemontools-qa #".length());
        result = result.substring(0, result.indexOf("[Jenkins]") - 1);
        int jenkinsid = Integer.parseInt(result);
        int id=jenkinsbll.getlastjenkinsdianzhangweifuwu().getid();
        int i = 0;
        while (i < 5 && id < jenkinsid) {
            jenkinsbll.insertjenkins(getjenkinsjenkinsdianzhangweifuwu(jenkinsid, Cookie));
            jenkinsid--;
            i++;
        }
    }

    private JenkinsBean getjenkinsjenkinsdianzhangweifuwu(int jenkinsid, String Cookie) {
        String url="https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_daemontools-qa/"+jenkinsid+"/parameters/";
        String x=doget1(url,"UTF-8",Cookie);
        JenkinsBean result=new JenkinsBean();
        x = x.substring(x.indexOf("value") + "value".length()+2);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        String git = x.substring(0,x.indexOf("\""));
        result.setgit(git);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        String feature = x.substring(0,x.indexOf("\""));
        result.setfeature(feature);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        String testenv = x.substring(0,x.indexOf("\""));
        result.settestenv(testenv);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        x = x.substring(x.indexOf("value") + "value".length()+2);
        String service = x.substring(0,x.indexOf("\""));
        result.setservice(service);
        result.setid(Integer.valueOf(jenkinsid));
        result.setCookie(Cookie);
        result.setschedule("0");
        result.setsource("2");
        result.setconsumer("0");
        String url3 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_daemontools-qa/"+jenkinsid+"/consoleText";
        String s = doget1(url3, "UTF-8", Cookie);
        String q = s.substring(s.indexOf("Started by user ") + 10, s.indexOf("Started by user ") + 100);
        result.setname(q.substring(q.indexOf("user ") + 5, q.indexOf("\n")));
        result.setstatus(s.substring(s.indexOf("Finished: ") + 10));
        String url5 = "https://ci.kanzhun-inc.com/view/dianzhang-qa/job/blue_daemontools-qa/"+jenkinsid;
        s = doget1(url5, "UTF-8", Cookie);
        s = s.substring(s.indexOf("Build #" + jenkinsid) + ("Build #" + jenkinsid).length());
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
        result.settime(s);

        return result;
    }

    private void getyfjenkins(String Cookie) {
        String url = "https://ci.kanzhun-inc.com/view/boss-preon/job/zhipin-service-preon/lastStableBuild/";
        String result = doget1(url, "UTF-8", Cookie);
        result = result.substring(result.indexOf("zhipin-service-preon #") + "zhipin-service-preon #".length());
        result = result.substring(0, result.indexOf("[Jenkins]") - 1);
        int jenkinsid = Integer.parseInt(result);
        int id=jenkinsbll.getlastjenkinsyufa();
            int i = 0;
            while (i < 5 && id < jenkinsid) {
                jenkinsbll.insertjenkins(getjenkinsyufa(jenkinsid, Cookie));
                jenkinsid--;
                i++;
            }
    }
    private void getweifuwujenkins(String Cookie,int aid,String mubiao) {
        String url = "https://ci.kanzhun-inc.com/view/zhipin-service/job/"+mubiao+"/lastStableBuild/";

        String result = doget1(url, "UTF-8", Cookie);
        result = result.substring(result.indexOf(mubiao+" #") + (mubiao+" #").length());
        result = result.substring(0, result.indexOf("[Jenkins]") - 1);
        int jenkinsid = Integer.parseInt(result);
        if (aid < jenkinsid) {
            int i = 0;
            while (i < 5 && aid < jenkinsid) {
                jenkinsbll.insertjenkins(getjenkins(jenkinsid, Cookie,mubiao));
                jenkinsid--;
                i++;
            }
        }
    }

    @Test
    public void test() {
        String Cookie="screenResolution=2560x1440; screenResolution=2560x1440; crowd.token_key=BVS0qN3NGOxyZqhf9MggnA00; JSESSIONID=91E7AE73FA6CC3FA45E3D53241D77159" ;
//        String url = "https://ci.kanzhun-inc.com/view/zhipin-service/job/zhipin-pay-service/lastStableBuild/";
        String url="https://ci.kanzhun-inc.com/job/zhipin-geek/3578/parameters/";
        String y=doget1(url,"UTF-8",Cookie);
//        System.out.println("urlyyy"+y);
//        y = y.substring(y.indexOf("value") + 7);
//        y = y.substring(y.indexOf("value") + 7);
//        String m=y;
////        System.out.println(m);
//        m = m.substring(m.indexOf("setting-input") + "setting-input".length()+5);
////        System.out.println(m);
//
//        m = m.substring(7,m.indexOf("/></td><td class=\"setting-no-help\"")-2);
//        System.out.println(m);
//
//        y = y.substring(0, y.indexOf("\""));
//        a.setid(Integer.valueOf(jenkinsid));
//        a.settestenv(testenv);
//        a.setservice(service);
//        a.setCookie(Cookie);
//        a.setschedule("0");
//        a.setconsumer("0");
//        a.setfeature(y+"("+m+")");
//        String url3 = "https://ci.kanzhun-inc.com/view/" + testenv2 + "/job/" + service2 + "/" + jenkinsid + "/consoleText";
//        String s = doget1(url3, "UTF-8", Cookie);
//        String q = s.substring(s.indexOf("Started by user ") + 10, s.indexOf("Started by user ") + 100);
//        a.setname(q.substring(q.indexOf("user ") + 5, q.indexOf("\n")));
//        a.setstatus(s.substring(s.indexOf("Finished: ") + 10));
//        String url5 = "https://ci.kanzhun-inc.com/view/" + testenv2 + "/job/" + service2 + "/" + jenkinsid;
//        s = doget1(url5, "UTF-8", Cookie);
//        s = s.substring(s.indexOf("Build #" + jenkinsid) + ("Build #" + jenkinsid).length());
//        s = s.substring(10, s.indexOf(")"));
//        s = s.replaceFirst("PM", "下午");
//        s = s.replaceFirst("AM", "上午");
//        s = s.replaceFirst("Jan", "一月");
//        s = s.replaceFirst("Feb", "二月");
//        s = s.replaceFirst("Mar", "三月");
//        s = s.replaceFirst("Apr", "四月");
//        s = s.replaceFirst("May", "五月");
//        s = s.replaceFirst("Jun", "六月");
//        s = s.replaceFirst("Jul", "七月");
//        s = s.replaceFirst("Aug", "八月");
//        s = s.replaceFirst("Sept", "九月");
//        s = s.replaceFirst("Oct", "十月");
//        s = s.replaceFirst("Nov", "十一月");
//        s = s.replaceFirst("Dec", "十二月");
//        a.settime(s);

//        System.out.println(jenkinsid);
    }
    @Test
    public void test2() {
        //qa1 新admin
        String Cookie=" crowd.token_key=qt6DTnuNm0mBuUG428cIUQ00; JSESSIONID=00A466E078E9FDECF07E9ABB24DF7156;" ;
        String url="https://ci.kanzhun-inc.com/view/boss-qa/job/boss-new-admin-qa/lastBuild/";
        String x=doget1(url,"UTF-8",Cookie);
        x=x.substring(x.indexOf("boss-new-admin-qa #")+("boss-new-admin-qa #").length());
        x=x.substring(0,x.indexOf("[Jenkins]")-1);
//        System.out.println(x);
        String url2="https://ci.kanzhun-inc.com/view/boss-qa/job/boss-new-admin-qa/"+x+"/parameters/";
        String y=doget1(url2,"UTF-8",Cookie);
        y=y.substring(y.indexOf("value")+7);
        y=y.substring(y.indexOf("value")+7);
        System.out.println(y.substring(0,y.indexOf("\"")));
        JenkinsBean a=new JenkinsBean();
        a.setid(Integer.valueOf(x));
        a.settestenv("qa");
        a.setservice("boss-new-admin-qa");
        a.setCookie(Cookie);
        a.setschedule("0");
        a.setconsumer("0");
        a.setfeature(y);
        String url3="https://ci.kanzhun-inc.com/view/boss-qa/job/boss-new-admin-qa/"+x+"/consoleText";
        String s=doget1(url3,"UTF-8",Cookie);
        String q=s.substring(s.indexOf("Started by user ")+10,s.indexOf("Started by user ")+100);
        System.out.println(q.substring(q.indexOf("user ")+5,q.indexOf("\n")));
        System.out.println(s.substring(s.indexOf("Finished: ")+10));

        String url5="https://ci.kanzhun-inc.com/view/boss-qa/job/boss-new-admin-qa/"+x;
        s=doget1(url5,"UTF-8",Cookie);
        s=s.substring(s.indexOf("Build #"+x)+("Build #"+x).length());
        s=s.substring(10,s.indexOf(")"));
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
        System.out.println(s);
    }
    @Test
    public void test5() {
        //qa2 新admin
        String Cookie=" crowd.token_key=qt6DTnuNm0mBuUG428cIUQ00; JSESSIONID=00A466E078E9FDECF07E9ABB24DF7156;" ;
        JenkinsBean a=new JenkinsBean();
        String url="https://ci.kanzhun-inc.com/view/zhipin-service/job/zhipin-pay-service/325/parameters/";
        String y=doget1(url,"UTF-8",Cookie);
        if(y.substring(y.indexOf("schedule"),y.indexOf("是否是schedule")).indexOf("checked=\"true\"")!=-1)
        {
            //存在schedule
            a.setschedule("1");
        }
        else
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
        y=y.substring(y.indexOf("value")+5);
        y=y.substring(y.indexOf("value")+7);
        a.setfeature(y.substring(0,y.indexOf("\"")));
        y=y.substring(y.indexOf("value")+5);
        y=y.substring(y.indexOf("value")+7);
        a.settestenv(y.substring(0,y.indexOf("\"")));
        String url2="https://ci.kanzhun-inc.com/view/zhipin-service/job/zhipin-pay-service/325/consoleText";
        String s=doget1(url2,"UTF-8",Cookie);
//        System.out.println(a);
//        System.out.println(s);
        String q=s.substring(s.indexOf("Started by user ")+10,s.indexOf("Started by user ")+100);
        a.setname(q.substring(q.indexOf("user ")+5,q.indexOf("\n")));
        a.setstatus(s.substring(s.indexOf("Finished: ")+10));
        a.setCookie(Cookie);
        a.setid(325);
        String url5="https://ci.kanzhun-inc.com/view/zhipin-service/job/zhipin-pay-service/325";
        s=doget1(url5,"UTF-8",Cookie);
        s=s.substring(s.indexOf("Build #"+"325")+("Build #"+"325").length()+10);
        System.out.println(s);
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
//        return a;
        System.out.println(a);
    }
    @Test
    public void test6() {
        //qa3 新admin
        String Cookie=" crowd.token_key=qt6DTnuNm0mBuUG428cIUQ00; JSESSIONID=00A466E078E9FDECF07E9ABB24DF7156;" ;
        String url="https://ci.kanzhun-inc.com/view/boss-qa3/job/techwolf-boss-newadmin-qa3/lastBuild/";
        String x=doget1(url,"UTF-8",Cookie);
        x=x.substring(x.indexOf("techwolf-boss-newadmin-qa3 #")+("techwolf-boss-newadmin-qa3 #").length());
        x=x.substring(0,x.indexOf("[Jenkins]")-1);
        System.out.println(x);
        String url2="https://ci.kanzhun-inc.com/view/boss-qa3/job/techwolf-boss-newadmin-qa3/"+x+"/parameters/";
        String y=doget1(url2,"UTF-8",Cookie);
        y=y.substring(y.indexOf("value")+7);
        y=y.substring(y.indexOf("value")+7);
        System.out.println(y.substring(0,y.indexOf("\"")));
    }
    @Test
    public void test3() {
        //qa1 老admin
        String Cookie=" crowd.token_key=qt6DTnuNm0mBuUG428cIUQ00; JSESSIONID=00A466E078E9FDECF07E9ABB24DF7156;" ;
        String url="https://ci.kanzhun-inc.com/view/boss-qa/job/techwolf-boss-qa/lastBuild/";
        String x=doget1(url,"UTF-8",Cookie);
        x=x.substring(x.indexOf("techwolf-boss-qa #")+("techwolf-boss-qa #").length());
        x=x.substring(0,x.indexOf("[Jenkins]")-1);
        System.out.println(x);
        String url2="https://ci.kanzhun-inc.com/view/boss-qa/job/techwolf-boss-qa/"+x+"/parameters/";
        String y=doget1(url2,"UTF-8",Cookie);
        y=y.substring(y.indexOf("value")+7);
        y=y.substring(y.indexOf("value")+7);
        System.out.println(y.substring(0,y.indexOf("\"")));
    }
    @Test
    public void test4() {
        //qa2 老admin
        String Cookie=" crowd.token_key=qt6DTnuNm0mBuUG428cIUQ00; JSESSIONID=00A466E078E9FDECF07E9ABB24DF7156;" ;
        String url="https://ci.kanzhun-inc.com/view/boss-qa2/job/techwolf-boss-qa2/lastBuild/";
        String x=doget1(url,"UTF-8",Cookie);
        x=x.substring(x.indexOf("techwolf-boss-qa2 #")+("techwolf-boss-qa2 #").length());
        x=x.substring(0,x.indexOf("[Jenkins]")-1);
        System.out.println(x);
        String url2="https://ci.kanzhun-inc.com/view/boss-qa2/job/techwolf-boss-qa2/"+x+"/parameters/";
        String y=doget1(url2,"UTF-8",Cookie);
        y=y.substring(y.indexOf("value")+7);
        y=y.substring(y.indexOf("value")+7);
        System.out.println(y.substring(0,y.indexOf("\"")));
    }
    @Test
    public void test7() {
        //qa3 老admin
        String Cookie=" crowd.token_key=qt6DTnuNm0mBuUG428cIUQ00; JSESSIONID=00A466E078E9FDECF07E9ABB24DF7156;" ;
        String url="https://ci.kanzhun-inc.com/view/boss-qa3/job/techwolf-boss-qa3/lastBuild/";
        String x=doget1(url,"UTF-8",Cookie);
        x=x.substring(x.indexOf("techwolf-boss-qa3 #")+("techwolf-boss-qa3 #").length());
        x=x.substring(0,x.indexOf("[Jenkins]")-1);
        System.out.println(x);
        String url2="https://ci.kanzhun-inc.com/view/boss-qa3/job/techwolf-boss-qa3/"+x+"/parameters/";
        String y=doget1(url2,"UTF-8",Cookie);
        y=y.substring(y.indexOf("value")+7);
        y=y.substring(y.indexOf("value")+7);
        System.out.println(y.substring(0,y.indexOf("\"")));
    }
    @Test
    public void test8() {
        //rd 老admin
        String Cookie=" crowd.token_key=qt6DTnuNm0mBuUG428cIUQ00; JSESSIONID=00A466E078E9FDECF07E9ABB24DF7156;" ;
        String url="https://ci.kanzhun-inc.com/view/boss-dev/job/boss-new-admin/lastBuild/";
        String x=doget1(url,"UTF-8",Cookie);
        x=x.substring(x.indexOf("boss-new-admin #")+("boss-new-admin #").length());
        x=x.substring(0,x.indexOf("[Jenkins]")-1);
        System.out.println(x);
        String url2="https://ci.kanzhun-inc.com/view/boss-dev/job/boss-new-admin/"+x+"/parameters/";
        String y=doget1(url2,"UTF-8",Cookie);
        y=y.substring(y.indexOf("value")+7);
        y=y.substring(y.indexOf("value")+7);
        System.out.println(y.substring(0,y.indexOf("\"")));
    }
    @Test
    public void test9() {
        //rd 老admin
        String Cookie=" crowd.token_key=qt6DTnuNm0mBuUG428cIUQ00; JSESSIONID=00A466E078E9FDECF07E9ABB24DF7156;" ;
        String url="https://ci.kanzhun-inc.com/view/boss-dev/job/techwolf-boss-dev/lastBuild/";
        String x=doget1(url,"UTF-8",Cookie);
        x=x.substring(x.indexOf("techwolf-boss-dev #")+("techwolf-boss-dev #").length());
        x=x.substring(0,x.indexOf("[Jenkins]")-1);
        System.out.println(x);
        String url2="https://ci.kanzhun-inc.com/view/boss-dev/job/techwolf-boss-dev/"+x+"/parameters/";
        String y=doget1(url2,"UTF-8",Cookie);
        y=y.substring(y.indexOf("value")+7);
        y=y.substring(y.indexOf("value")+7);
        System.out.println(y.substring(0,y.indexOf("\"")));
    }
}