package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.toolsManager.AppInterfaceLogBean;
import cn.boss.platform.bll.toolsManager.AppInterfaceLogBll;
import net.sf.json.JSONObject;

import javax.websocket.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2019/12/26.
 */
public class AppInterfaceLogThread implements Runnable{

    //用户id
    private String userIds;
    //链接session
    private  Session session=null;

    private AppInterfaceLogBll appInterfaceLogBll;

    private int sum;
    private int new_sum;
    private boolean stopMe = true;
    public void stopMe() {
        stopMe = false;
    }

    public AppInterfaceLogThread(String userIds, Session session,AppInterfaceLogBll appInterfaceLogBll) {
        this.userIds = userIds;
        this.session = session;
        this.appInterfaceLogBll = appInterfaceLogBll;
    }

    @Override
    public void run() {
        //userId转list
        List<Object> userIdList = Arrays.asList(userIds.split(","));
        sum = appInterfaceLogBll.getInterfaceLogCountByUserIds(userIdList,null);
        try {
            while(stopMe) {
                Thread.sleep(300);
                new_sum = appInterfaceLogBll.getInterfaceLogCountByUserIds(userIdList, null);
                if (sum != new_sum) {
                    System.out.println("change");
                    List<AppInterfaceLogBean> list = appInterfaceLogBll.getLogNew(userIdList,null,(new_sum)-sum);
                    List<Object> listNew = new ArrayList<>();
                    for(int i = 0 ; i < list.size() ; i++) {
                        if(list.get(i) != null){
                            listNew.add(JSONObject.fromObject(list.get(i)).toString());
                            session.getBasicRemote().sendText(JSONObject.fromObject(list.get(i)).toString());
                        }
                    }
                    sum = new_sum;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}