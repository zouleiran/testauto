package cn.boss.platform.bll.kanzhun;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/10/18.
 */
public class KanzhunUtil {

//    @Autowired
//    private static ExecuteService executeService;
//    public static String KEY = "3170d32aa0f7e62f93be5a67fc643ebf";
    public final static String KEY = "ANDROID2017Nubility";
    private final static String PARAM_FORMAT = "v=%s&c=%s&b=%s";

    private static JsonMapper mapper = JsonMapper.buildNonNullMapper();

    /**
     * 看准接口测试
     */
    private static void testHttp() {
        // 业务参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", 2571229);
        map.put("pageIndex", 0);
        map.put("friendId", 11074190);
        map.put("requestId", 0);
        map.put("type", 1);
        map.put("itemId", 429193);
        map.put("reasonIds", "1,2");
        String bString = createBparam(map);

        // 公共参数
        String cString = createCparam("company.index.v2", 11074328, "123"); //
        String url = "http://kanzhun-api.weizhipin.com/api";

        // 加密
        ClientDe androidDe = ClientDe.ANDROID;
        // SecurityUtils.rc4Encrypt(string, key)
        String secretC = androidDe.encode(cString, androidDe.getCk());
        String secretB = androidDe.encode(bString, KEY);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("v",androidDe.getIdentify());
        paramMap.put("c",secretC);
        paramMap.put("b",secretB);

        Response response = send(url,paramMap,null,"GET");
        // format: v=%s&c=%s&b=%s
        final String postBody = String.format(PARAM_FORMAT, androidDe.getIdentify(), secretC, secretB);
        System.out.println(response.toString());
        System.out.println(response);
        System.out.println(androidDe.decode(response.asString(), KEY));
    }

    /**
     * 看准接口参数加密
     * @param path
     * @param bparam
     * @param cparam
     * @return
     */
    public static Map<String, Object> kanzhunSign(String path, Map<String, Object> bparam, Map<String, Object> cparam){
        String apiName = path.replaceAll("/",".");
        if(apiName.substring(0,1).equals(".")){
            apiName = apiName.substring(1,apiName.length());
        }
        // 公共参数
        String cString = createCparam(apiName, 11074328, "123");
//        String url = "http://kanzhun-api.weizhipin.com/api";
        // 业务参数
        String bString = createBparam(bparam);
        // 加密
        ClientDe androidDe = ClientDe.ANDROID;
        String secretC = androidDe.encode(cString, androidDe.getCk());
        String secretB = androidDe.encode(bString, KEY);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("v",androidDe.getIdentify());
        paramMap.put("c",secretC);
        paramMap.put("b",secretB);
        return paramMap;
    }

    /**
     * 创建c参数
     *
     * @param apiName
     * @return
     */
    private static String createCparam(String apiName, long userId, String ticket) {
        CommonParam cp = new CommonParam();
        cp.setModel("");
        cp.setChannel(1);
        cp.setMac("");
        cp.setNetwork("");
        cp.setUniqid("");
        cp.setVersion("1");
        cp.setScreen("");
        cp.setKey(KEY);

        cp.setT(apiName);
        cp.setUserid(userId);
        cp.setTicket(ticket);

        return mapper.toJson(cp);
    }

    /**
     * 创建B参数
     *
     * @param param
     * @return
     */
    private static String createBparam(Object param) {
        return mapper.toJson(param);
    }




    /**
     * 发送http请求
     * @param url
     * @param method
     * @return
     */
    public static Response send(@Nonnull String url, Map<String, Object> params, Map<String, Object> headers, @Nonnull String method) {
        try {
            RestAssured.registerParser("text/plain", Parser.JSON);
            RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("utf-8"));

            RequestSpecification requestSpecification = RestAssured.given();
            //以json格式接收数据
            requestSpecification.headers("Accept", "application/JSON");
            if (headers != null) {
                requestSpecification.headers(headers);
            }
            if (params != null) {
                requestSpecification.formParams(params);
            }
            switch (method.toUpperCase()) {
                case "GET":
                    return requestSpecification.get(url);
                case "POST":
                    return requestSpecification.post(url);
                case "PUT":
                    return requestSpecification.put(url);
                case "DELETE":
                    return requestSpecification.delete(url);
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }



    public static void main(String[] args) {
        String path = "validActiveCode";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account","14000010004");
        map.put("type","1");
        map.put("code","3456");
        map.put("regionCode","+86");
        String url = "http://kanzhun-api.weizhipin.com/api";

        Map<String, Object> header = new HashMap<String, Object>();
        header.put("resp-encode", "un-encode");
        Response response = send(url,KanzhunUtil.kanzhunSign(path,map,null),header,"POST");

//        response.then().body("code", equalTo("2"));
        // format: v=%s&c=%s&b=%s
//        final String postBody = String.format(PARAM_FORMAT, androidDe.getIdentify(), secretC, secretB);
        ClientDe androidDe = ClientDe.ANDROID;
        System.out.println(response.asString());
//        System.out.println(androidDe.decode(response.asString(), KEY));



    }



}
