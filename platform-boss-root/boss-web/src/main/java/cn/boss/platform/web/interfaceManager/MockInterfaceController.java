package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.MockInterfaceBean;
import cn.boss.platform.bll.InterfaceManager.MockInterfaceBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.MockInterfaceForm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/3/21.
 */
@Controller
@RequestMapping("/boss/advmock")
public class MockInterfaceController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(MockInterfaceController.class);

    @Autowired
    MockInterfaceBll mockInterfaceBll;

    /**
     * 添加mock接口
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@Valid MockInterfaceForm form, BindingResult bindingResult) {
        logger.info("/boss/advmock/add POST 方法被调用!!" + form.toString());
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            MockInterfaceBean mockInterfaceBean = new MockInterfaceBean();
            BeanUtils.copyProperties(form, mockInterfaceBean);
            mockInterfaceBean.setCreateTime(new Date());
            mockInterfaceBean.setUpdateTime(new Date());
            mockInterfaceBll.addMockInterface(mockInterfaceBean);
            return ResultBean.success("添加接口成功!");
        } catch (Exception e) {
            logger.error("添加接口异常：{}", e);
            return ResultBean.failed("添加接口失败!");
        }
    }

    /**
     * 查询mock接口
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value = "interfaceId",required = false) Integer interfaceId,@RequestParam(value = "id",required = false) Integer id) {
        try {
            logger.info("/case/list GET 方法被调用!! param: " + interfaceId);
            List<MockInterfaceBean> list = mockInterfaceBll.getMockInterface(interfaceId,id);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("mockInterfaceList", list);
//                put("caseCount", count);
            }};
            return ResultBean.success("查询mock接口成功!", result);
        } catch (Exception e) {
            logger.error("查询mock接口异常：{}", e);
            return ResultBean.failed("查询mock接口异常!");
        }
    }


    /**
     * 删除接口，有用例的接口不能删除
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        logger.info("/interface/delete GET 方法被调用!!");
        try {
            mockInterfaceBll.deleteMockInterface(id);
            return ResultBean.success("删除mock接口成功！");
        } catch (Exception e) {
            logger.error("删除mock接口异常：{" + e + "}");
            return ResultBean.failed("删除mock接口异常!");
        }
    }

    /**
     * 更新mock接口
     * @param form
     * @param br
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> update(@Valid MockInterfaceForm form, BindingResult br) {
        logger.info("/interface/update POST 方法被调用!!", form);
        try{
            String errorMsg = validateData(br);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            MockInterfaceBean mockInterfaceBean = new MockInterfaceBean();
            BeanUtils.copyProperties(form,mockInterfaceBean);
            mockInterfaceBean.setUpdateTime(new Date());
            if(mockInterfaceBll.updateMockInterface(mockInterfaceBean)){
                return ResultBean.success("更新mock接口成功!!");
            }
            return ResultBean.failed("更新mock接口失败!!");
        } catch (Exception e) {
            logger.error("更新mock接口异常：{" + e + "}");
            return ResultBean.failed("更新mock接口异常!");
        }
    }


}
