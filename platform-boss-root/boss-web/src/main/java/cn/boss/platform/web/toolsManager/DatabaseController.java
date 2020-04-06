package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bll.util.SecurityUtils;
import cn.boss.platform.service.tools.DatabaseService;
import cn.boss.platform.web.api.AbstractBaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


/**
 * Created by caosenquan on 2018/4/13.
 */

@Controller
@RequestMapping("/tools")
public class DatabaseController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    /**
     * 执行qa数据库操作
     * @return
     */
    @RequestMapping(value = "/data/query", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> query(@Valid DatabaseForm databaseForm, BindingResult bindingResult)  {
        logger.info("/data/query POST 方法被调用!!");
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            String sql = databaseForm.getSql().toLowerCase();
            DatabaseService database = new DatabaseService(databaseForm.getEnv(),databaseForm.getDb());
            if (sql.indexOf("select") ==0 ){
                return ResultBean.success("查询"+databaseForm.getEnv()+"数据成功！",database.select(sql,databaseForm.getPageIndex(),databaseForm.getPageSize()));
            }else if(sql.indexOf("update") == 0 || sql.indexOf("delete") == 0 || sql.indexOf("insert into") == 0 || sql.indexOf("create") == 0|| sql.indexOf("alter") == 0){
                return ResultBean.success("执行"+databaseForm.getEnv()+"数据成功！",database.update(sql));
            }
            else{
                return ResultBean.failed("sql语句有误！");
            }
        } catch (Exception e) {
            logger.error("添加用例异常：{}", e);
            return ResultBean.failed("执行失败!");
        }
    }


    @RequestMapping(value = "/userId/encrypt", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> userIdEncrypt(@RequestParam("userId") String userId) {
        try {
            String encrypt = SecurityUtils.encryptString(userId);
            return ResultBean.success("userId加密成功!",encrypt);
        } catch (Exception e) {
            logger.error("查询日志异常：{}", e);
            return ResultBean.failed("userId加密异常!");
        }
    }




}
