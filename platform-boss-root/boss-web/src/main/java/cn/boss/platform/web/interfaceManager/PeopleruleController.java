package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.PeopleruleBean;
import cn.boss.platform.bean.interfaceManager.RuleBean;
import cn.boss.platform.bll.InterfaceManager.PeopleruleBll;
import cn.boss.platform.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.*;

@Controller
@RequestMapping("/boss/peoplerule")
public class PeopleruleController extends AbstractBaseController {
    private static final Logger logger = LoggerFactory.getLogger(EnvController.class);
    @Resource
    private PeopleruleBll peopleruleBll;
    @RequestMapping(value = "/searchweburlbyemail", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="email") String email) {
        try {
            logger.info("/boss/peoplerule/searchweburlbyemail GET 方法被调用!! param: " +email);
            List list = peopleruleBll.searchweburlbyemail(email);
            int listCount = list ==null ? 0 : list.size();
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("weburl", list);
                put("weburlCount", listCount);
            }};
            return ResultBean.success("查询角色权限成功!", result);
        } catch (Exception e) {
            logger.error("查询角色权限异常：{}", e);
            return ResultBean.failed("查询角色权限异常!");
        }
    }
    @RequestMapping(value = "/searchrulebypeopleid", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="id") Integer id) {
        try {
            logger.info("/boss/peoplerule/searchwebbyruleid GET 方法被调用!! param: " +id);
            List<RuleBean> list = peopleruleBll.searchrulebypeopleid(id);
            int listCount = list ==null ? 0 : list.size();
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("RuleList1", list);
                put("RuleCount", listCount);
            }};
            return ResultBean.success("查询角色权限成功!", result);
        } catch (Exception e) {
            logger.error("查询角色权限异常：{}", e);
            return ResultBean.failed("查询角色权限异常!");
        }
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> peoplerulelist(@RequestParam(value="item",required=false) Integer id,
                                             @RequestParam(value="email",required=false) String email,
                                             @RequestParam(value="username",required=false) String username,
                                   @RequestParam("pageIndex") Integer pageIndex,
                                   @RequestParam("pageSize") Integer pageSize) {
        try {
            logger.info("/boss/peoplerule/list GET 方法被调用!! param: " +pageIndex,pageSize);
            List<PeopleruleBean> list = peopleruleBll.querypeoplerule(id,email,username,pageIndex, pageSize);
            int listCount = list ==null ? 0 : list.size();
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("PeopleruleList", list);
                put("PeopleruleCount", listCount);
            }};
            return ResultBean.success("查询用户成功!", result);
        } catch (Exception e) {
            logger.error("查询用户异常：{}", e);
            return ResultBean.failed("查询用户异常!");
        }
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="id") Integer id) {
        logger.info("/boss/peoplerule/delete GET 方法被调用!!");
        try {
            peopleruleBll.deletepeoplerule(id);
            return ResultBean.success("删除用户成功!");
            }
         catch (Exception e) {
            logger.error("删除用户ip异常：{" + e + "}");
            return ResultBean.failed("删除用户异常!");
        }
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@RequestParam(value="username") String username,
                                  @RequestParam(value="email") String email) {
        logger.info("/boss/peoplerule/add POST 方法被调用!!" + email);
        try {
            PeopleruleBean a=new PeopleruleBean();
            a.setusername(username);
            a.setyn("1");
            a.setemail(email);
            a.setruleid("4");
            peopleruleBll.addpeoplerule(a);
            return ResultBean.success("添加人员成功!");
        } catch (Exception e) {
            logger.error("添加人员异常：{}", e);
            return ResultBean.failed("添加人员失败!");
        }
    }
//    {"list":[{"id":1,"webid":"1","ruledes":"普通用户","yn":"1"},{"id":2,"webid":"1,2","ruledes":"z","yn":"0"},{"id":3,"webid":"3","ruledes":"z","yn":"0"},{"id":4,"webid":null,"ruledes":"系统管理员","yn":"0"}],"id":5,"ruledes":"","yn":"","RuleCount":4}

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> update(@RequestParam(value="a") String a) {
        logger.info("/boss/peoplerule/update get 方法被调用!!"+a);
        try{
            JSONObject json1 = JSON.parseObject(a);
            int id=json1.getInteger("id");
            String result="";
            StringBuilder ruleid=new StringBuilder();
            JSONArray json2 = JSON.parseArray(json1.getString("list"));
            for (int i=0;i<json2.size();i++)
            {
                JSONObject json3= (JSONObject) json2.get(i);
                if (json3.getString("yn").equals("1"))
                    ruleid.append(json3.getInteger("id")+",");
            }
            if (ruleid.length()==0)
                ruleid.append("4,");
            else
                result=ruleid.toString().substring(0,ruleid.length()-1);
            PeopleruleBean peopleruleBean=new PeopleruleBean();
            peopleruleBean.setruleid(result);
            peopleruleBean.setid(id);
            peopleruleBll.updatepeoplerule(peopleruleBean);
            return ResultBean.success("更新用户角色成功！");
        } catch (Exception e) {
            logger.error("更新用户角色异常：{}", e);
            return ResultBean.failed("更新用户角色失败!");
        }
    }
}
