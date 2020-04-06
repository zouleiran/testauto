package cn.boss.platform.web.validator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JSONValidator implements ConstraintValidator<JSON, String> {

  @Override
  public void initialize(JSON constraintAnnotation) {

  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    try {
      JSONObject.parse(s);
    } catch (JSONException ex) {
      try {
        JSONArray.parse(s);
      } catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }
}
