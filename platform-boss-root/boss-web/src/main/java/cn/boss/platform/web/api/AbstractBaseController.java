package cn.boss.platform.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

/**
 * <p>
 *   AbstractBaseController描述
 * </p>
 *
 * @author caosenquan
 * @since 0.0.1
 */
public class AbstractBaseController {

  /**
   * 日志记录
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseController.class);

  /**
   * @Title: validateData @Description: 验证数据 @param bindResult 绑定数据 @return
   *         ErrorMessageDictionary @throws
   */
  public String validateData(BindingResult bindResult) {
    List<ObjectError> errorList = bindResult.getAllErrors();
    LOGGER.info("validate field: {}", errorList);
    if (errorList != null && !errorList.isEmpty()) {
      return errorList.get(0).getDefaultMessage();
    }
    return "";
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }
}
