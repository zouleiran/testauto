package cn.boss.platform.bll.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 *
 *版权所有：北京光宇在线科技有限责任公司
 *创建时间：2017年6月30日 上午9:36:32
 *类名：签名类
 * @author caosenquan
 */
public class MD5Util {

	/**
	 * 签名方法
	 * @param url
	 * @param key
	 * @return
	 */
//	public static String sign(String url, String key) {
//		//Log.logInfo("接口请求地址" + url);
//		URI uri = URI.create(url);
//
//		String timestamp = "timestamp=" + timestamp();
//		TreeSet<String> set = Sets.newTreeSet();
//		set.add(timestamp);
//
//		boolean hasQuery = uri.getQuery() != null && uri.getQuery().length() > 0;
//		if (hasQuery) {
//			set.addAll(Arrays.asList(uri.getQuery().split("&")));
//		}
//
//		boolean first = true;
//		StringBuilder builder = new StringBuilder(uri.getPath());
//		for (String pair : set) {
//			//值为空的参数不参与签名，切记，不然报签名错误的
//			if(pair.split("=").length == 2) {
//				builder.append(first ? "?" : "&").append(pair);
//				first = false;
//			}
//		}
//		builder.append(key);
//		return url+(hasQuery?"&":"?")+ timestamp + "&sign_type=MD5&sign="	+ md5(builder.toString());
//	}

	/**
	 * 摇一摇活动md5签名方法
	 * @param parameter
	 * @param key
	 * @return
	 */
	public static String activeSign(String parameter,String key){
		String timestamp = "time=" + timestamp();
		//获取url链接路径
		StringBuilder builder = new StringBuilder();
		if(builder !=null){
			//签名
			builder.append(parameter).append("&").append(timestamp).append(key);
		}
		return timestamp+"&sign="+md5(builder.toString());
	}


	/**
	 * 时间戳生成
	 * @return
	 */
	public static long timestamp() {
		return new Date().getTime() / 1000;
	}

	/**
	 * 当前时间
	 * @return
	 */
	public static long time(String time) {
		return new Date().getTime();
	}

	/**
	 * md5加密方法
	 * @param text
	 * @return
	 */
	public static String md5(String text) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(text));
			return String.format("%032x", new BigInteger(1, md5.digest()));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成l_key值
	 * @param device_id
	 * @return
	 */
	public static String L_key(String device_id){
		//A_key值
		String A_key="a9160e43e3017b3de70cd2ba54bb821k";
		String timestamp=timestamp()+"";
		//String L_key = toMD5(A_key + device_id + timestamp())+("" + timestamp()).substring(0,8);
		String L_key = md5(A_key + device_id + timestamp)+timestamp.substring(0,8);
		return L_key;

	}


	public static void main(String[] args) {
		String ResponseCode="2323";


	}
}