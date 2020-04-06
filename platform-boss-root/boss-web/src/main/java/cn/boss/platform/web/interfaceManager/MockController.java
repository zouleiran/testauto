package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.MockInterfaceBean;
import cn.boss.platform.bll.InterfaceManager.MockInterfaceBll;
import cn.boss.platform.web.api.AbstractBaseController;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admin on 2019/3/21.
 */
@Controller
@RequestMapping("/mock")
public class MockController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(MockController.class);

    @Autowired
    MockInterfaceBll mockInterfaceBll;

    /**
     * mock服务
     */
    @RequestMapping(value = "/**", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object mock(HttpServletRequest request) {
        try{
            String url;
            if(request.getServerPort() == 80){
                url = request.getScheme() +"://" + request.getServerName()  + request.getServletPath();
            }else{
                url = request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort() + request.getServletPath();
            }
            List<MockInterfaceBean> list =  mockInterfaceBll.getMockInterfaceByUrl(url);
            if(list.size() == 0){
                logger.error("mock 接口不存在！");
                return ResultBean.failed("mock 接口不存在！");
            }
            return JSONObject.parseObject(list.get(0).getResponseResult());
//            if (!(list.get(0).getMethod().toUpperCase()).equals(request.getMethod().toUpperCase())){
//                return ResultBean.failed("请求方法错误,该接口只支持" + list.get(0).getMethod() + "请求！");
//            }else{
//                return JSONObject.parseObject(list.get(0).getResponseResult());
//            }
        } catch (Exception e) {
            logger.error("mock异常：{}", e);
            return ResultBean.failed("mock失败!");
        }
    }



}
