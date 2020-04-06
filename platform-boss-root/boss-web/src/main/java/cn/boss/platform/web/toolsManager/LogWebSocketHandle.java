package cn.boss.platform.web.toolsManager;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import org.junit.Test;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

@ServerEndpoint("/log/{param}")
public class LogWebSocketHandle {

    private Process process;
    private InputStream inputStream;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session,@PathParam(value="param") String param) {
        System.out.println("zlr"+param);
        try {
            if (param.endsWith("2")) {//此处是在走正常日志监控
                System.out.println(param + "zlr");
                if (!param.startsWith("ip="))
                    System.out.println("error");
                param = param.substring(3);
                String host = param.substring(0, param.indexOf("&"));
                System.out.println(host);
                param = param.substring(param.indexOf("&") + 6);
                String port = param.substring(0, param.indexOf("&"));
                System.out.println(port);
                param = param.substring(param.indexOf("&") + 6);
                String user = param.substring(0, param.indexOf("&"));
                param = param.substring(param.indexOf("&") + 10);
                System.out.println(user);
                String password = param.substring(0, param.indexOf("shell=") - 1);
                param = param.substring(param.indexOf("shell=") + 6);
                String command = param.substring(0, param.indexOf("&")).replaceAll("\\[", "/");
                System.out.println(password);
                System.out.println(command);
            JSch jsch = new JSch();
            com.jcraft.jsch.Session session1 = jsch.getSession(user, host, Integer.parseInt(port));
            session1.setConfig("StrictHostKeyChecking", "no");
            session1.setPassword(password);
            session1.connect();

            ChannelExec channelExec = (ChannelExec) session1.openChannel("exec");
            InputStream in = channelExec.getInputStream();
            channelExec.setCommand(command);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            inputStream = in;
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
            }
            else{//埋点的请求
                System.out.println("zlr1234"+param);
                String[] a=param.split("&");
                String uid = null;
                String action= null;
                String topic;
                String server;
                if(a[0].split("=").length==2)//uid存在
                    uid=a[0].split("=")[1];
                if(a[1].split("=").length==2)//action存在
                    action=a[1].split("=")[1];
                topic=a[2].split("=")[1];
                server=a[3].split("=")[1];
//                String type=a[0].split("=")[1];
//                String value2=a[1].split("=")[1];
//                System.out.println(type);
//                System.out.println(value2);
                String command=null;
                if (uid!=null&&action!=null)//存在uid+action
                    command="source /etc/profile&& /data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server "+server+" --topic "+topic+"|stdbuf -oL awk '{if($0~\"\\\"action\\\":\\\""+action+"\\\"\"&&$0~\"\\\"uid\\\":"+uid+"\") print}'";
                else if (action!=null)//存在action
                    command="source /etc/profile&& /data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server "+server+" --topic "+topic+"|stdbuf -oL grep "+action;
                else
                    command="source /etc/profile&& /data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server "+server+" --topic "+topic+"|stdbuf -oL grep \\\"uid\\\":"+uid;
                System.out.println(command);
//                /data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server george-01:9092 --topic qa_bg_action_3 | awk '{if($0~"\"action\":\"gf2-tab\""&&$0~"\"uid\":6461642") print}'
//                source /etc/profile&& /data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server george-01:9092 --topic qa_bg_action_3|stdbuf -oL grep \"uid\":6
//                String param="uid=1&action=&topic=qa_bg_action_3&type=1";
//                if(type.equals("uid"))//uid
//                {
////                    command="tail -f /home/qa_test/1.txt|stdbuf -oL grep \\\"uid\\\":"+value2;
//                    command="source /etc/profile&& /data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server george-01:9092 --topic qa_bg_action_3|stdbuf -oL grep \\\"uid\\\":"+value2;
//                    System.out.println(command);
//                }
//                else//action
//                {
////                    command="tail -f /home/qa_test/1.txt|grep "+a[2].split("=")[1];
//                    command="source /etc/profile&& /data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server george-01:9092 --topic qa_bg_action_3|stdbuf -oL grep "+value2;
////                    command="tail -f /home/qa_test/1.txt|stdbuf -oL grep "+value2;
//                    System.out.println(command);
//                }
//                command="tail -f /home/qa_test/1.txt";
                String host="192.168.1.71";
                String user="qa_test";
                String password="Q@^a66Tes%2";
                int port=22;
                JSch jsch = new JSch();
                com.jcraft.jsch.Session session1 = jsch.getSession(user, host,port);
                session1.setConfig("StrictHostKeyChecking", "no");
                session1.setPassword(password);
                session1.connect();

                ChannelExec channelExec = (ChannelExec) session1.openChannel("exec");
                InputStream in = channelExec.getInputStream();
                channelExec.setCommand(command);
                channelExec.setErrStream(System.err);
                channelExec.connect();
                System.out.println(in.toString());
                inputStream = in;
                TailLogThread thread = new TailLogThread(inputStream, session);
                thread.start();
                System.out.println("ok");
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        }
    }
    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        System.out.println("123close");
        try {
            System.out.println("1234close");
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(process != null)
            process.destroy();
    }

    @OnError
    public void onError(Throwable thr) {

        System.out.println("1234error");
        thr.printStackTrace();
    }
    @Test
    public void test2() {
        String param="stype=1&" + "value2=f1-tab&"+"type=" + 1;
        System.out.println(param);
        String[] a=param.split("&");
        System.out.println(a.length);
        System.out.println(a[0]);
        System.out.println(a[1]);
        String type=a[0].split("=")[1];
        String value2=a[1].split("=")[1];
        System.out.println(type);
        System.out.println(value2);
    }
    @Test
    public void test() {
        String param="uid=1&action=&topic=qa_bg_action_3&type=1";
        String[] a=param.split("&");
        String[] b=a[0].split("=");
        System.out.println(b.length);
        String type=a[0].split("=")[0];
        System.out.println(type);
        String command=null;
        if(type.equals("1"))//uid
        {
            command="/data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server george-01:9092 --topic qa_bg_action_3|grep \\\"uid\\\":"+a[1].split("=")[1];
            System.out.println(command);
        }
        else//action
        {
            command="/data1/kafka_2.11-2.0.0/bin/kafka-console-consumer.sh --bootstrap-server george-01:9092 --topic qa_bg_action_3|grep "+a[2].split("=")[1];
            System.out.println(command);
        }
        System.out.println(command+"2");
    }
}