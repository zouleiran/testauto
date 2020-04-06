package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.*;
import cn.boss.platform.bll.InterfaceManager.RuleBll;
import cn.boss.platform.web.api.AbstractBaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/boss/rule")
public class RuleController extends AbstractBaseController {
    private static final Logger logger = LoggerFactory.getLogger(EnvController.class);
    @Autowired
    private RuleBll ruleBll;
    @RequestMapping(value = "/searchwebbyruleid", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> searchwebbyruleid(@RequestParam(value="id") Integer id) {
        try {
            logger.info("/boss/rule/searchwebbyruleid GET 方法被调用!! param: " +id);
            List<FirstWebBean> list = ruleBll.searchwebidbyruleid(id);
            int listCount = list ==null ? 0 : list.size();
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("WebList", list);
                put("WebCount", listCount);
            }};
            return ResultBean.success("查询角色权限成功!", result);
        } catch (Exception e) {
            logger.error("查询角色权限异常：{}", e);
            return ResultBean.failed("查询角色权限异常!");
        }
    }
//    {"id":1,"WebList":[{"firstid":4,"firstdes":"验证码","ruleBean":[{"id":1,"weburl":"123","webdes":"验证码2","parent_id":null,"yn":"1"},{"id":2,"weburl":"124","webdes":"验证码3","parent_id":null,"yn":"0"}]},{"firstid":5,"firstdes":"吃饭","ruleBean":[{"id":3,"weburl":"225","webdes":"吃饭了","parent_id":null,"yn":"0"}]}]}
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> update(@RequestParam(value="a") String a) {
        try {
            logger.info("/boss/rule/update GET 方法被调用!! param: " +a);
            JSONObject json1 = JSON.parseObject(a);
            int id=json1.getInteger("id");
            JSONArray WebList= JSON.parseArray(json1.getString("WebList"));
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<WebList.size();i++)
            {
                JSONObject Json= (JSONObject) WebList.get(i);
                JSONArray ruleBean1= JSON.parseArray(Json.getString("ruleBean"));
                for(int j=0;j<ruleBean1.size();j++)
                {
                    JSONObject Json2= (JSONObject) ruleBean1.get(j);
                    if(Json2.getString("yn").equals("1"))
                        sb.append(Json2.getInteger("id")+",");
                }
            }
            if (sb.length()==0)
                sb.append("7,");
            String ruleid=sb.toString().substring(0,sb.length()-1);
            ruleBll.update(id,ruleid);
            return ResultBean.success("更新权限成功!");
        } catch (Exception e) {
            logger.error("更新权限失败：{}", e);
            return ResultBean.failed("更新权限失败!");
        }
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="id",required=false) Integer id,
                                   @RequestParam(value="ruledes",required=false) String ruledes,
                                   @RequestParam("pageIndex") Integer pageIndex,
                                   @RequestParam("pageSize") Integer pageSize) {
        try {
            logger.info("/boss/rule/list GET 方法被调用!! param: " +pageIndex,pageSize);
            List<RuleBean> list = ruleBll.queryrule(id,ruledes,pageIndex, pageSize);
            int listCount = list ==null ? 0 : list.size();
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("RuleList", list);
                put("RuleCount", listCount);
            }};
            return ResultBean.success("查询角色成功!", result);
        } catch (Exception e) {
            logger.error("查询角色异常：{}", e);
            return ResultBean.failed("查询角色异常!");
        }
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@RequestParam(value="ruledes") String ruledes) {
        logger.info("/boss/rule/add POST 方法被调用!!" + ruledes);
        try {
            RuleBean a=new RuleBean();
            a.setruledes(ruledes);
            a.setwebid("7");
            a.setyn("1");
            ruleBll.addrule(a);
            return ResultBean.success("添加角色成功!");
        } catch (Exception e) {
            logger.error("添加角色异常：{}", e);
            return ResultBean.failed("添加角色异常!");
        }
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="id") Integer id) {
        logger.info("/boss/rule/delete GET 方法被调用!!");
        try {
            ruleBll.deleterule(id);
            return ResultBean.success("删除角色成功!");
        }
        catch (Exception e) {
            logger.error("删除角色异常：{" + e + "}");
            return ResultBean.failed("删除角色异常!");
        }
    }
}
