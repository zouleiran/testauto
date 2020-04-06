package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.toolsManager.AppInterfaceLogBean;
import cn.boss.platform.bll.toolsManager.AppInterfaceLogBll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2019/12/6.
 */
@Component
@ServerEndpoint(value = "/appLog/{userId}")
public class AppInterfaceLogController {

    private static final Logger logger = LoggerFactory.getLogger(HealthUrlController.class);

    private static AppInterfaceLogBll appInterfaceLogBll;

    @Autowired
    public void setAppInterfaceLogBll(AppInterfaceLogBll appInterfaceLogBll){
        AppInterfaceLogController.appInterfaceLogBll = appInterfaceLogBll;
    }

    private AppInterfaceLogThread appInterfaceLogThread;
    private static String userId;
    private  Session session=null;


    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("userId") String userIds,Session session) throws IOException{
        this.userId = userId;
        this.session = session;
        appInterfaceLogThread = new AppInterfaceLogThread(userIds,session,appInterfaceLogBll);
        Thread thread=new Thread(appInterfaceLogThread);
        thread.start();
        logger.debug("新连接：{}",userId);
    }

    //关闭时执行
    @OnClose
    public void onClose() {
        logger.debug("连接：{} 关闭",this.userId);
        appInterfaceLogThread.stopMe();
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.debug("收到用户{}的消息{}",this.userId,message);
//        List<AppInterfaceLogBean> list = appInterfaceLogBll.getLog(Integer.valueOf(userId),null,1,100);
//        session.getBasicRemote().sendText(list+""); //回复用户

    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error){
        logger.debug("用户id为：{}的连接发送错误",this.userId);
        error.printStackTrace();
    }




}