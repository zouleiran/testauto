package cn.boss.platform.service.interfaceManager;


import com.alibaba.fastjson.JSON;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by admin on 2018/12/21.
 */
@Service
public class JsonPathService {

    private static final Logger logger = LoggerFactory.getLogger(JsonPathService.class);


    public static String JSON1 = "{ \"store\": {\n" +
            "    \"book\": [ \n" +
            "      { \"category\": \"reference\",\n" +
            "        \"author\": \"Nigel Rees\",\n" +
            "        \"title\": \"Sayings of the Century\",\n" +
            "        \"price\": 8.95\n" +
            "      },\n" +
            "      { \"category\": \"fiction\",\n" +
            "        \"author\": \"Evelyn Waugh\",\n" +
            "        \"title\": \"Sword of Honour\",\n" +
            "        \"price\": 12\n" +
            "      },\n" +
            "      { \"category\": \"fiction\",\n" +
            "        \"author\": \"Herman Melville\",\n" +
            "        \"title\": \"Moby Dick\",\n" +
            "        \"isbn\": \"0-553-21311-3\",\n" +
            "        \"price\": 8.99\n" +
            "      },\n" +
            "      { \"category\": \"fiction\",\n" +
            "        \"author\": \"J. R. R. Tolkien\",\n" +
            "        \"title\": \"The Lord of the Rings\",\n" +
            "        \"isbn\": \"0-395-19395-8\",\n" +
            "        \"price\": 22.99\n" +
            "      }\n" +
            "    ],\n" +
            "    \"bicycle\": {\n" +
            "      \"color\": \"red\",\n" +
            "      \"price\": 19.95,\n" +
            "      \"atoms\": " + Long.MAX_VALUE + ",\n" +
            "    }\n" +
            "  }\n" +
            "}";

//    public static void main(String[] args) {
//        try {
//            String json = "{\"hasItem\":{\"store.book.category\":\"reference1\"}}";
//
//            JsonPathService.assertDubboRest(JSON1,json);
//        }catch (AssertionError e){
//            System.out.println("12121212");
//        }
//
//    }


    public void assertDubboRest(String json, String expected) {
        Map<String, Object> sonVaule ;
        Map<String, Object> expectedResult = JSON.parseObject(expected);
        for (String key : expectedResult.keySet()) {
            sonVaule = JSON.parseObject(expectedResult.get(key) + "");
            //相等校验
            if (key.equals("equalTo")) {
                for (String subKey : sonVaule.keySet()) {
//                    final String realValue = JsonPath.with(json).get(subKey);
                    assertThat(JsonPath.with(json).get(subKey), equalTo(sonVaule.get(subKey)));
                }
            }
            //list包含校验
            else if(key.equals("hasItem")){
                for (String subKey : sonVaule.keySet()) {
                    final List<String> categories = JsonPath.with(json).get(subKey);
                    assertThat(categories, hasItems(sonVaule.get(subKey)+""));
                }
            }
        }
    }
}
