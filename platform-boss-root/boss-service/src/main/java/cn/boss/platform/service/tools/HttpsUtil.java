package cn.boss.platform.service.tools;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class HttpsUtil {

    private static boolean proxySet = true;
    private static String proxyHost = "127.0.0.1";
    private static int proxyPort = 8080;

    public static void main(String[] args) {
        String url="https://ci.kanzhun-inc.com/view/zhipin-service/job/zhipin-service/lastBuild/";

//        String url = "https://www.google.com";
        String params = "";

        String getResult = HttpsUtil.sendGet(url, params, proxySet);
//        System.out.println(getResult);
    }

    //设置请求头属性
    public static Map<String, String> setProperty() {

        HashMap<String, String> pMap = new HashMap<>();
        String Cookie="screenResolution=2560x1440; jenkins-timestamper-offset=-28800000; screenResolution=2560x1440; JSESSIONID=6EDD7148852B0D58B86A63A60C5A421D; ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE=em91bGVpcmFuQGthbnpodW4uY29tOjE1NjA5MjY3Mjg3MTc6ZWNlY2ZhNGY5YzVmNTgwYTk0NzU4NDIwY2IxZGE1NmI2OTQ2ZWQzNzZhNTNkN2EzZWYwODY0ZTVhMmYzMGJlMw==";
        // pMap.put("Accept-Encoding", "gzip"); //请求定义gzip,响应也是压缩包
        pMap.put("connection", "Keep-Alive");
        pMap.put("Cookie", Cookie);
        pMap.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        pMap.put("Content-Type", "application/x-www-form-urlencoded");
        return pMap;
    }

    /**
     * GET请求
     *
     * @param url
     *            请求的URL
     * @param param
     *            请求参数，name1=value1&name2=value2 的形式
     * @return 响应结果
     */
    public static String sendGet(String url, String param, boolean isproxy) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (param != null && !("".equals(param)))
                urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = null;
            if (isproxy) {// 使用代理模式
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                connection = (HttpURLConnection) realUrl.openConnection(proxy);
            } else {
                connection = (HttpURLConnection) realUrl.openConnection();
            }

            // https 忽略证书验证
            if (url.substring(0, 5).equals("https")) {
                SSLContext ctx = MyX509TrustManagerUtils();
                ((HttpsURLConnection) connection).setSSLSocketFactory(ctx.getSocketFactory());
                ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            }

            // 设置通用的请求属性
            for (Map.Entry<String, String> entry : setProperty().entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            // 建立连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK
                    || connection.getResponseCode() == HttpURLConnection.HTTP_CREATED
                    || connection.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！");
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数 name1=value1&name2=value2 的形式
     * @param isproxy
     *            是否使用代理模式
     * @return 响应结果
     */
    public static String sendPost(String url, String param, boolean isproxy) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            if (isproxy) {// 使用代理模式
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (HttpURLConnection) realUrl.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) realUrl.openConnection();
            }

            // https
            if (url.substring(0, 5).equals("https")) {
                SSLContext ctx = MyX509TrustManagerUtils();
                ((HttpsURLConnection) conn).setSSLSocketFactory(ctx.getSocketFactory());
                ((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
                    //在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            }

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // POST方法

            // 设置通用的请求属性

            for (Map.Entry<String, String> entry : setProperty().entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.connect();

            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK
                    || conn.getResponseCode() == HttpURLConnection.HTTP_CREATED
                    || conn.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！");
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String urlEncode(String source, String encode) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }

    /*
     * HTTPS忽略证书验证,防止高版本jdk因证书算法不符合约束条件,使用继承X509ExtendedTrustManager的方式
     */
    class MyX509TrustManager extends X509ExtendedTrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

    }

    public static SSLContext MyX509TrustManagerUtils() {

        TrustManager[] tm = { new HttpsUtil().new MyX509TrustManager() };
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctx;
    }

}