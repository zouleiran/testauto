package cn.boss.platform.bll.util;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2018/8/10.
 */
public class Function {

    /**
     * 时间戳生成
     * @return
     */
    public static long timestamp() {
        return new Date().getTime();
    }

    /**
     * 当前时间
     * @return
     */
    public static long time(String time) {
        return new Date().getTime();
    }

    /**
     * 登录密码加密
     * @param data
     * @return
     */
    public static String password(String data){
        String key = "9f738a3934abf88b1dca8e8092043fbd";
        return SecurityUtils.rc4Encrypt(data,key);
    }

    /**
     * 获取client_info参数
     * @return
     */
    public static Object clientInfo(){
        Map<String,Object> CLIENT_INFO_MAP=new HashMap<String,Object>();
        CLIENT_INFO_MAP.put("channel", "0");
        CLIENT_INFO_MAP.put("longitude", "116.4459800720");
        CLIENT_INFO_MAP.put("os", "iOS");
        CLIENT_INFO_MAP.put("model", "iPhone6,2");
        CLIENT_INFO_MAP.put("start_time", "1475213324420");
        CLIENT_INFO_MAP.put("latitude", "39.9701807845");
        CLIENT_INFO_MAP.put("version", "9.3.5");
        CLIENT_INFO_MAP.put("resume_time", "1475233065102");
        CLIENT_INFO_MAP.put("idfa", "9C4E21D4-FA02-4300-9272-QA");
        Object clientInfo = JSONObject.toJSON(CLIENT_INFO_MAP);
        return clientInfo;
    }

    /**
     * 获取特定时间，格式：yyyy-MM-dd
     * @return
     */
    public static String appointTime(String time){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,Integer.parseInt(time));//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);

    }


    public static void main(String[] args) {
        String parameters = "${__appointTime(1)}";
        String spiltRules = "\\+|,|\\*|/|=|\\(|\\)";
        String[] array = parameters.split(spiltRules);
        System.out.println(array[0]);
        System.out.println(array[1]);
    }

}
