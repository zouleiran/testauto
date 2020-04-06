package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.JenkinsBean;
import cn.boss.platform.dao.interfaceManager.JenkinsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class JenkinsBll {
    @Resource
    private JenkinsMapper jenkinsMapper;

    public JenkinsBean getlastjenkins() {
        return jenkinsMapper.getlastjenkins();
    }
    public void insertjenkins(JenkinsBean b) {
        jenkinsMapper.insertjenkins(b);
    }

    public List<JenkinsBean> selectJenkins2(String testenv, String service, int begin, int size,String source) {
        return jenkinsMapper.selectJenkins2(testenv,service,begin,size,source);
    }

//    public List<JenkinsBean> selectJenkins2(JenkinsBean b) {
//        return jenkinsMapper.selectJenkins2(b);
//    }

    public void setCookie(String cookie) {
        jenkinsMapper.setCookie(cookie);
    }

    public List<JenkinsBean> getlastJenkins(String testenv,String source) {
        return jenkinsMapper.getlastJenkins(testenv,source);
    }

    public int getlastjenkinsadmin(String testenv, String service) {
        return jenkinsMapper.getlastjenkinsadmin(testenv,service);
    }
    public int getlastjenkinsyufa() {
        return jenkinsMapper.getlastjenkinsyufa();
    }

    public JenkinsBean getlastjenkinspay() {
        return jenkinsMapper.getlastjenkinspay();
    }

    public JenkinsBean getlastjenkinsdianzhangadmin() {
        return jenkinsMapper.getlastjenkinsdianzhangadmin();
    }

    public JenkinsBean getlastjenkinsdianzhangweifuwu() {
        return jenkinsMapper.getlastjenkinsdianzhangweifuwu();
    }
//    public List<JenkinsBean> selectJenkins2(String testenv, String service) {
//        return jenkinsMapper.selectJenkins2(b);
//    }
}
