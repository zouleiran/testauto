package cn.boss.platform.web.tools.dianzhang;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.service.interfaceManager.ExecuteService;
import cn.boss.platform.service.parameterManager.ParameterService;
import cn.boss.platform.web.form.DebugForm;
import cn.boss.platform.web.form.RequestForm;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static cn.boss.platform.service.tools.HttpClientUtil.doget1;

/**
 * <p>
 *   AbstractBaseController描述
 * </p>
 *
 * @author caosenquan
 * @since 0.0.1
 */
@Controller
@RequestMapping("/")
public class SetCode {
  @Autowired
  private ParameterService parameterService;
  @Autowired
  private ExecuteService executeService;
  /**
   * 日志记录
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SetCode.class);

  @RequestMapping(value = "/dianzhang/setcode", method = RequestMethod.POST)
  @ResponseBody
  public ResultBean<DebugForm> validateData(@Valid RequestForm form, BindingResult bindingResult) throws InterruptedException {
    try {
      LOGGER.info("/dianzhang/setcode方法被调用" + form);
      String params = form.getParameter();
      params = parameterService.evalParameter(params);
      Map<String, Object> headerMap = new HashMap<>();
      Map<String, Object> paramerMap = JSON.parseObject(params);
      String env=(String)paramerMap.get("env");
      String x = doget1("https://"+env, paramerMap, "UTF-8", "t=WhMws9Dn2nJ1bKh; wt=WhMws9Dn2nJ1bKh; JSESSIONID=D82489EBF7D0CC1D41DDE98756C5EF2A; admin_uluk=5cd2b40c-321f-4832-8aee-884c311a30d5");
      DebugForm debug = new DebugForm();
      debug.setResponse(x);
      return ResultBean.success("接口调用成功！", debug);
    } catch (InterruptedException e) {
      return ResultBean.failed("接口调用失败!");
    }
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }
}
