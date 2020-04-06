package cn.boss.platform.bll.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jayway.restassured.RestAssured.get;


/**
 * 正则表达式匹配两个指定字符串中间的内容，用于关联使用
 */
public class DealStrSub {  
    /** 
     * 正则表达式匹配两个指定字符串中间的内容 
     * @param soap 
     * @return 
     */  
    public static List<String> getSubUtil(String soap,String rgex){
        try{
            List<String> list = new ArrayList<String>();
            Pattern pattern = Pattern.compile(rgex);// 匹配的模式
            Matcher m = pattern.matcher(soap);
            while (m.find()) {
                int i = 1;
                list.add(m.group(i));
                i++;
            }
            return list;
        }catch (Exception e) {
            return null;
        }

    }  
      
    /** 
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样 
     * @param soap 
     * @param rgex 
     * @return 
     */  
    public static String getSubUtilSimple(String soap,String rgex){
        try{
            Pattern pattern = Pattern.compile(rgex);// 匹配的模式
            Matcher m = pattern.matcher(soap);
            while(m.find()){
                return m.group(1);
            }
            return "";
        }catch (Exception e) {
            return "";
        }

    }  
      
    /** 
     * 测试 
     * @param args 
     */  
    public static void main(String[] args) {  
    	String url ="http://qa.module.gyyx.cn/D_key/encode?content=autotest008&D_key=rju4tBiw4DB60X5o9lxm6aqjYlyRe1txbQNaiaN7";
    	String result = get(url).asString();
        String rgex = "data\":\"(.*?),";  
        System.out.println(getSubUtil(result,rgex));  
        System.out.println(getSubUtilSimple(result, rgex));  
    }    
}  