package cn.boss.platform.web.tools;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.service.tools.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/redis")
public class RedisController {
    @Resource
    RedisService redisService;
    @GetMapping(value = "/executesdel")
    @ResponseBody
    public ResultBean<Object> del(@RequestParam("statement") String statement,@RequestParam("env") String env) throws IOException {
        try{
//            System.out.println(statement);
            redisService.delkey(env,statement);
            return ResultBean.success("删除缓存成功!");
        } catch (Exception e) {
            return ResultBean.failed("删除缓存异常!");
        }
    }
    @GetMapping(value = "/executesget")
    @ResponseBody
    public ResultBean<Object> get(@RequestParam("statement") String statement,@RequestParam("env") String env) throws IOException {
        try{
            String result=redisService.getkey(env,statement);
            System.out.println(result);
            return ResultBean.success("查询缓存成功!", result);
        } catch (Exception e) {
            return ResultBean.failed("查询缓存异常!");
        }
    }
}