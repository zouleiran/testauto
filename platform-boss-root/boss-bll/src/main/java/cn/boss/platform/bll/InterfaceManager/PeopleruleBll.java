package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.PeopleruleBean;
import cn.boss.platform.bean.interfaceManager.RuleBean;
import cn.boss.platform.dao.interfaceManager.PeopleruleMapper;
import cn.boss.platform.dao.interfaceManager.RuleMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class PeopleruleBll {
    @Resource
    private PeopleruleMapper peopleruleMapper;
    @Resource
    private RuleMapper ruleMapper;
    public List<PeopleruleBean> querypeoplerule(Integer id,String username,String email, int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return peopleruleMapper.querypeoplerule(id,username,email,skip, pageSize);
    }

    public void deletepeoplerule(Integer id) {
        peopleruleMapper.deletepeoplerule(id);
    }
    public void updatepeoplerule(PeopleruleBean PeopleruleBean){
        peopleruleMapper.updatepeoplerule(PeopleruleBean);
    }

    public void addpeoplerule(PeopleruleBean a) {
        peopleruleMapper.addpeoplerule(a);
    }

    public List<RuleBean> searchrulebypeopleid(Integer id) {
        List x1=new ArrayList();
        List<RuleBean> result1=peopleruleMapper.searchrule();
        String result2=peopleruleMapper.searchrulebypeopleid(id);
        String[] x;
        if (result2.length()==0)
            x=null;
        else {
            x = result2.split(",");
        }
        for(int i=0;i<x.length;i++)
            x1.add(x[i]);
        for (int i=0;i<result1.size();i++) {
            if (x1.contains(result1.get(i).getid().toString())) {
                result1.get(i).setyn("1");
            }
            else
                result1.get(i).setyn("0");
        }
        return result1;
    }
    public String searchweburlbyemail1(String email) {
//        return null;
        String result2=peopleruleMapper.searchrulebyemail(email);
        Set<Integer> set1=new HashSet();
        if(result2==null)
            return "";
        String[] x = result2.split(",");
        for(int i=0;i<x.length;i++)
        {
            String y=ruleMapper.searchwebbyid(Integer.valueOf(x[i]));
            String[] y1=y.split(",");
            for(int j=0;j<y1.length;j++)
            {
                set1.add(Integer.valueOf(y1[j]));
            }
        }
        List result=new ArrayList();
        for(int id:set1)
        {

            String z=ruleMapper.searchweburlbywebid(id);
            result.add(z);
        }
        return result.toString();
    }
    public List<String> searchweburlbyemail(String email) {
//        return null;
        String result2=peopleruleMapper.searchrulebyemail(email);
        Set<Integer> set1=new HashSet();
        String[] x = result2.split(",");
        for(int i=0;i<x.length;i++)
        {
            String y=ruleMapper.searchwebbyid(Integer.valueOf(x[i]));
            String[] y1=y.split(",");
            for(int j=0;j<y1.length;j++)
            {
                set1.add(Integer.valueOf(y1[j]));
            }
        }
        List result=new ArrayList();
        for(int id:set1)
        {

            String z=ruleMapper.searchweburlbywebid(id);
            result.add(z);
        }
        return result;
    }
}
