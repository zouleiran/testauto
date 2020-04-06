package cn.boss.platform.service.interfaceManager;

import com.alibaba.fastjson.JSON;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * Created by admin on 2018/4/27.
 */
@Service
public class RestAssertService {

    private static final Logger logger = LoggerFactory.getLogger(RestAssertService.class);

    public void assertRest(Response response, String expected) {
        Map<String, Object> sonVaule = null;
        Map<String, Object> expectedResult = JSON.parseObject(expected);
        for (String key : expectedResult.keySet()) {
            try {
                sonVaule = JSON.parseObject(expectedResult.get(key) + "");
            } catch (Exception e) {
                response.then().body(key, equalTo(expectedResult.get(key)));
                logger.info("校验为老版本！" + e.getMessage());
            }
            //为空校验
            if (key.equals("notNullValue")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, notNullValue());
                }
            }
//            //包含-解决解析不了的json
//            else if (key.equals("contains")) {
//                for (String subKey : sonVaule.keySet()) {
//                    response.toString().contains(sonVaule.get(subKey)+"");
//                }
//            }
            //包含校验
            else if (key.equals("containsString")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, containsString(sonVaule.get(subKey) + ""));
                }
            }
            //list包含校验
            else if (key.equals("hasItem")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, hasItem(sonVaule.get(subKey)));
                }
            }
            //相等校验
            else if (key.equals("equalTo")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, equalTo(sonVaule.get(subKey)));
                }
            }
            //是否有以什么开头
            else if (key.equals("startsWith")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, startsWith((sonVaule.get(subKey) + "")));
                }
            }
            //是否以什么结尾
            else if (key.equals("endsWith")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, endsWith((sonVaule.get(subKey) + "")));
                }
            }
            //小于或等于校验
            else if (key.equals("lessThanOrEqualTo")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, lessThanOrEqualTo(Integer.valueOf(sonVaule.get(subKey) + "")));
                }
            }
            //大于或等于校验
            else if (key.equals("greaterThanOrEqualTo")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, greaterThanOrEqualTo(Integer.valueOf(sonVaule.get(subKey) + "")));
                }
            }
            //数组长度
            else if (key.equals("hasSize")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, hasSize(Integer.valueOf(sonVaule.get(subKey) + "")));
                }
            }
            //是否有key
            else if (key.equals("hasKey")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, hasKey(equalTo(sonVaule.get(subKey))));
                }
            }
            //是否有value值
            else if (key.equals("hasValue")) {
                for (String subKey : sonVaule.keySet()) {
                    response.then().body(subKey, hasValue(equalTo(sonVaule.get(subKey))));
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.trimToEmpty("123131"));
    }

}



