package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.FirstWebBean;
import cn.boss.platform.bean.interfaceManager.RuleBean;
import cn.boss.platform.bean.interfaceManager.WebBean;
import cn.boss.platform.dao.interfaceManager.RuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class RuleBll {
    @Autowired
    private RuleMapper ruleMapper;
    public List<RuleBean> queryrule(Integer id, String ruledes,int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return ruleMapper.queryrule(id,ruledes,skip, pageSize);
    }

    public void deleterule(Integer id) {
        ruleMapper.deleterule(id);
    }

    public void addrule(RuleBean a) {
        ruleMapper.addrule(a);
    }

    public List<FirstWebBean> searchwebidbyruleid(Integer id) {
        List x1=new ArrayList();
        List result=new ArrayList();
        List<WebBean> result1=ruleMapper.searchweb();
        List first=new ArrayList();
        for(int i=0;i<result1.size();i++)
            if(result1.get(i).getparent_id()==0)
                first.add(i);
            //这里找出1及目录，后用id去查其下所有的二级目录
        String result2=ruleMapper.searchwebbyid(id);
        String[] x=result2.split(",");
        if (x.length>0)
            for(int i=0;i<x.length;i++)
                x1.add(x[i]);
        for(int i=0;i<first.size();i++)
        {
            int y=(int)first.get(i);
            int ID=result1.get(y).getid();
            FirstWebBean FirstWebBean=new FirstWebBean();
            FirstWebBean.setfirstid(ID);
            FirstWebBean.setfirstdes(result1.get(y).getwebdes());
            List zhongjian=new ArrayList();
            for(int j=0;j<result1.size();j++)
            {
                if (result1.get(j).getparent_id()==ID)
                {
                    WebBean WebBean=new WebBean();
                    WebBean.setid(result1.get(j).getid());
                    WebBean.setwebdes(result1.get(j).getwebdes());
                    WebBean.setweburl(result1.get(j).getweburl());
                    if (x1.contains(result1.get(j).getid().toString()))
                        WebBean.setyn("1");
                    else
                        WebBean.setyn("0");
                    zhongjian.add(WebBean);
                }
            }
            FirstWebBean.setRuleBean(zhongjian);
            result.add(FirstWebBean);
        }
        return result;
    }

    public void update(int id, String ruleid) {
        ruleMapper.update(id,ruleid);
    }
}
