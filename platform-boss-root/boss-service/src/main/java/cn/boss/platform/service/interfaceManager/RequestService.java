package cn.boss.platform.service.interfaceManager;


import cn.boss.platform.bll.util.SignUtil;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *   SendRequest描述
 * </p>
 *
 * @author chenwen
 * @since 0.0.1
 */
@Service
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    /**
     * boss直播接口
     * 拼接参数并且生成sig参数值
     * @param url
     * @param params
     * @param secretKey
     */
    public  String getSigUrl(String url,String params,String secretKey,Integer item){
        String sc = "9f738a3934abf88b1dca8e8092043fbd";
//        String sc = "a308f3628b3f39f7d35cdebeb6920e21";
        try {
            String Parames = params;
            String newUrl = url;
            String sig = null;
            //拼接参数
            if(!StringUtils.isBlank(params)){
                //排序
                params = SignUtil.getSortJson(params);
                Parames = SignUtil.signStringAllParams(new ObjectMapper().readValue(params, Map.class), "");
                String newParames = SignUtil.signStringAllParams(
                        new ObjectMapper().readValue(params, Map.class), "&");
                newUrl = String.format("%s?%s", url,newParames);
            }
            //签名
            URL uri = new URL(newUrl);
            String path = uri.getPath();
            //非登录接口签名
            if(StringUtils.isBlank(secretKey)){
                secretKey = "";
            }
            if(item.equals(1)){
                //接口签名
                sig = "V2.0"+ DigestUtils.md5Hex(path + Parames +  sc+secretKey ).toLowerCase();
            }else if(item.equals(2)){
                sig = DigestUtils.md5Hex(path + Parames +  sc+secretKey ).toLowerCase();
            }

            return newUrl + "&sig=" + sig;
        } catch (Exception e) {
            logger.error("获取组装请求URL异常{}",e);
            return null;
        }
    }

    /**
     * boss直聘接口
     * 拼接参数并且生成sig参数值
     * @param url
     * @param params
     * @param secretKey
     * @return
     */
    public  String getSig(String url,String params,String secretKey){
        String sc = "9f738a3934abf88b1dca8e8092043fbd";
        try {
            String Parames = params;
            String newUrl = url;
            String sig ;
            //拼接参数
//            Parames =generateNormalizedString(new ObjectMapper().readValue(Parames, Map.class));
            if(!StringUtils.isBlank(params)){
                //排序
                Parames = SignUtil.getSortJson(params);
                Parames = SignUtil.signStringAllParams(new ObjectMapper().readValue(Parames, Map.class), "");
            }
            //签名
            URL uri = new URL(url);
            String path = uri.getPath();
            //非登录接口签名
            if(StringUtils.isBlank(secretKey)){
                secretKey = "";
            }
            //接口签名
            String giger = path + Parames +  sc+secretKey;
            sig = "V2.0"+ DigestUtils.md5Hex(path + Parames + sc+secretKey).toLowerCase();
            return sig;
        } catch (Exception e) {
            logger.error("获取组装请求URL异常{}",e);
            return null;
        }
    }

    /**
     * 店长直聘接口
     * 拼接参数并且生成sig参数值
     * @param url
     * @param params
     * @param secretKey
     * @return
     */
    public  String getDianZhangSig(String url,String params,String secretKey){
        String sc = "9f738a3934abf88b1dca8e8092043fbd";
        try {
            String Parames = params;
            String newUrl = url;
            String sig ;
            //拼接参数
//            Parames =generateNormalizedString(new ObjectMapper().readValue(Parames, Map.class));
            if(!StringUtils.isBlank(params)){
                //排序
                Parames = SignUtil.getSortJson(params);
                Parames = SignUtil.signStringAllParams(new ObjectMapper().readValue(Parames, Map.class), "");
            }
            //签名
            URL uri = new URL(url);
            String path = uri.getPath();
            //非登录接口签名
            if(StringUtils.isBlank(secretKey)){
                secretKey = "";
            }
            //接口签名
            String giger = path + Parames +  sc+secretKey;
            sig = DigestUtils.md5Hex(path + Parames + sc+secretKey).toLowerCase();
            return sig;
        } catch (Exception e) {
            logger.error("获取组装请求URL异常{}",e);
            return null;
        }
    }

    /**
     * 生成clientinfo
     * @return
     */
    public static String getClientInfo(int projectId){
        if(projectId == 1){
            Map<String,Object> CLIENT_INFO_MAP=new HashMap<String,Object>();
            CLIENT_INFO_MAP.put("channel", "0");
            CLIENT_INFO_MAP.put("longitude", "22121.290000");
            CLIENT_INFO_MAP.put("os", "iOS");
            CLIENT_INFO_MAP.put("model", "iPhone6,2");
            CLIENT_INFO_MAP.put("start_time", "1475213324420");
            CLIENT_INFO_MAP.put("latitude", "2231.140000");
            CLIENT_INFO_MAP.put("version", "9.3.5");
            CLIENT_INFO_MAP.put("resume_time", "1475233065102");
            CLIENT_INFO_MAP.put("idfa", "9C4E21D4-FA02-4300-9272");
            Object clientInfo = JSONObject.toJSON(CLIENT_INFO_MAP);
            return clientInfo.toString();
        }else{
            return "";
        }
    }

    public static void main(String[] args) {
        Date d=new Date();
        System.out.println(d.getMonth());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy0MM");
        System.out.println(sdf.format(d));
    }


}
