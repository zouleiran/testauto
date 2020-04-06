package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.ShellBean;
import cn.boss.platform.dao.toolsManager.ShellMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.boss.platform.service.tools.HttpClientUtil.doposthttp;

@Controller
@RequestMapping("/tools/shell")
public class ShellController {
    @Resource
    ShellMapper shellMapper;
    @RequestMapping(value = "/getshell", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> encrypt(@RequestParam("servername") String servername,
                                      @RequestParam("env") String env)  {
        try {
//            return ResultBean.success("手机号加密成功！", server);
            ShellBean result=shellMapper.getshell(servername,env);
            String ip[]=result.gethost().split(",");
            List a=new ArrayList<>();
            for (int i=0;i<ip.length;i++)
                a.add(ip[i]);
            String shell="tail -f "+result.getaccess();
            result.setshell(shell);
            Map<String, Object> result1 = new HashMap<String, Object>() {{
                put("ShellBean", result);
                put("iplist", a);
            }};
            return ResultBean.success("获取参数成功！", result1);
        }catch (Exception e) {
            return ResultBean.failed("获取参数失败！");
        }
    }
    @RequestMapping(value = "/getstfurl", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getstfurl(@RequestParam("name") String name,
                                      @RequestParam("email") String email)  {
        try {
//            return ResultBean.success("手机号加密成功！", server);
            String url="http://172.16.24.12:7100/auth/api/v1/mock";
            String parm="{\"name\":\""+name+"\",\"email\":\""+email+"\"}";
            String srfurl=doposthttp(url,parm);
            JSONObject json = JSON.parseObject(srfurl);
//            JSON.parseArray(json, HashMap.class);
//            System.out.println(json.getString("redirect"));
//            ShellBean result=shellMapper.getshell(servername,env);
//            String ip[]=result.gethost().split(",");
//            List a=new ArrayList<>();
//            for (int i=0;i<ip.length;i++)
//                a.add(ip[i]);
//            String shell="tail -f "+result.getaccess();
//            result.setshell(shell);
            Map<String, Object> result1 = new HashMap<String, Object>() {{
                put("stfurl", json.getString("redirect"));
            }};
            return ResultBean.success("获取参数成功！", result1);
        }catch (Exception e) {
            return ResultBean.failed("获取参数失败！");
        }
    }
}
