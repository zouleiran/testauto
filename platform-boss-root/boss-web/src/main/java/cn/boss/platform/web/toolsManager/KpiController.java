package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bll.util.ConnectionDB;
import cn.boss.platform.service.tools
        .DataField;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.model.TesterKpiModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by admin on 2019/6/27.
 */
@Controller
@RequestMapping("/boss/kpi")
public class KpiController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    /**
     * 测试绩效考核表
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/listV2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> kpiTestListV2(@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime)  {
        logger.info("/boss/kpi/list GET 方法被调用!!");
        try{
            if(StringUtils.isEmpty(startTime)){
                startTime = "2010-01-01";
            }
            if(StringUtils.isEmpty(endTime)){
                endTime = "2050-01-01";
            }
            List<TesterKpiModel> kpiModelsList = new ArrayList<>();
            ConnectionDB twldb = new ConnectionDB(DataField.jdbc_driver,DataField.twl_url,DataField.username,DataField.password);
            ConnectionDB db = new ConnectionDB(DataField.jdbc_driver,DataField.jira_url,DataField.jiraUsername,DataField.jiraPassword);
            String user = "select * from boss_tools_jira_user_tb where identity ='qa'";
            List<Map<String,String>> userSelect = twldb.select(user);
            String username = "";
            //拼接所有人
            for (Map<String,String> userMap : userSelect) {
                username = username + "'" +userMap.get("email") + "',";
            }
            if(!StringUtils.isEmpty(username)){
                username = username.substring(0, username.length()-1);
            }
            //bug总数
            String bugCount = "select count(*) totalCount ,REPORTER  from jiraissue WHERE REPORTER in ("+ username +") and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'" + " group by REPORTER";
            List<Map<String,String>> bugCountSelect = db.select(bugCount);
            //任务总数
            String taskCount = "select count(*) taskCount, count(*) *(50) count1, ASSIGNEE from jiraissue where PROJECT='11205' and ASSIGNEE in ("+ username +")  and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'"+ " group by ASSIGNEE";
            List<Map<String,String>> taskCountSelect = db.select(taskCount);
            //p1数
            String pOneSelect = "select count(*) p1Count,count(*) *(25) count1 ,REPORTER from jiraissue where PRIORITY='1' and RESOLUTION not in ('3','4','5') and  REPORTER in ("+ username +")  and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'" + " group by REPORTER";
            List<Map<String,String>> pOneCountSelect = db.select(pOneSelect);
            //p2数
            String pTwoSelect = "select count(*) p2Count,count(*) *(20) count1,REPORTER from jiraissue where PRIORITY='2' and RESOLUTION not in ('3','4','5') and REPORTER in ("+ username +")  and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'"+ " group by REPORTER";
            List<Map<String,String>> pTwoCountSelect = db.select(pTwoSelect);
            //p3数
            String pThreeSelect = "select count(*) p3Count,count(*) *(15) count1,REPORTER from jiraissue where PRIORITY='3' and RESOLUTION not in ('3','4','5') and REPORTER in ("+ username +")  and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'"+ " group by REPORTER";
            List<Map<String,String>> pThreeCountSelect = db.select(pThreeSelect);
            //p4数
            String pFourSelect = "select count(*) p4Count,count(*) *(10) count1,REPORTER from jiraissue where PRIORITY='4' and RESOLUTION not in ('3','4','5')  and REPORTER in ("+ username +")  and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'"+ " group by REPORTER";
            List<Map<String,String>> pFourCountSelect = db.select(pFourSelect);
            //p5数
            String pFiveSelect = "select count(*) p5Count,count(*) *(5) count1,REPORTER from jiraissue where PRIORITY='5' and RESOLUTION not in ('3','4','5') and REPORTER in ("+ username +") and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'"+ " group by REPORTER";
            List<Map<String,String>> pFiveCountSelect = db.select(pFiveSelect);
            //未解决数
            String pNoSelect = "select count(*) openCount,count(*) *(10) count1,REPORTER from jiraissue where  RESOLUTION IS NULL and REPORTER in ("+ username +")  and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'"+ " group by REPORTER";
            List<Map<String,String>> pNoCountSelect = db.select(pNoSelect);
            //无效数
            String pInvalidSelect = "select count(*) invalidCount,count(*) *(-15) count1,REPORTER from jiraissue where RESOLUTION in ('3','4','5')  and REPORTER in ("+ username +")  and CREATED >='" + startTime + "'" + " and CREATED <='" + endTime + "'"+ " group by REPORTER";
            List<Map<String,String>> pInvalidCountSelect = db.select(pInvalidSelect);


            for(Map<String,String> userMap : userSelect){
                TesterKpiModel kpiModel = new TesterKpiModel();
                Double score = 0.00;
                kpiModel.setName(userMap.get("name"));
                kpiModel.setEmail(userMap.get("email"));
                for(Map<String,String> bugCountMap : bugCountSelect) {
                    if (bugCountMap.get("REPORTER").equals(userMap.get("email"))) {
                        kpiModel.setBugCount(Integer.parseInt(bugCountMap.get("totalCount")));
                    }
                }
                for(Map<String,String> taskCountMap : taskCountSelect){
                    if(userMap.get("email").equals(taskCountMap.get("ASSIGNEE"))){
                        kpiModel.setTaskCount(Integer.parseInt(taskCountMap.get("taskCount")));
                        score =  Double.parseDouble(taskCountMap.get("count1"));
                    }
                }
                for(Map<String,String> pOneCountMap : pOneCountSelect) {
                    if (userMap.get("email").equals(pOneCountMap.get("REPORTER"))) {
                        kpiModel.setPoneBug(Integer.parseInt(pOneCountMap.get("p1Count")));
                        score = score + Double.parseDouble(pOneCountMap.get("count1"));
                    }
                }
                for(Map<String,String> pTwoCountMap : pTwoCountSelect) {
                    if (userMap.get("email").equals(pTwoCountMap.get("REPORTER"))) {
                        kpiModel.setPtwoBug(Integer.parseInt(pTwoCountMap.get("p2Count")));
                        score = score + Double.parseDouble(pTwoCountMap.get("count1"));
                    }
                }
                for(Map<String,String> pThreeCountMap : pThreeCountSelect) {
                    if (userMap.get("email").equals(pThreeCountMap.get("REPORTER"))) {
                        kpiModel.setPthreeBug(Integer.parseInt(pThreeCountMap.get("p3Count")));
                        score = score + Double.parseDouble(pThreeCountMap.get("count1"));
                    }
                }
                for(Map<String,String> pFourCountMap : pFourCountSelect) {
                    if (userMap.get("email").equals(pFourCountMap.get("REPORTER"))) {
                        kpiModel.setPfourBug(Integer.parseInt(pFourCountMap.get("p4Count")));
                        score = score + Double.parseDouble(pFourCountMap.get("count1"));
                    }
                }
                for(Map<String,String> pFiveCountMap : pFiveCountSelect) {
                    if (userMap.get("email").equals(pFiveCountMap.get("REPORTER"))) {
                        kpiModel.setPfiveBug(Integer.parseInt(pFiveCountMap.get("p5Count")));
                        score = score + Double.parseDouble(pFiveCountMap.get("count1"));
                    }
                }
                for(Map<String,String> pNoCountMap : pNoCountSelect) {
                    if (userMap.get("email").equals(pNoCountMap.get("REPORTER"))) {
                        kpiModel.setPnoBug(Integer.parseInt(pNoCountMap.get("openCount")));
                        score = score + Double.parseDouble(pNoCountMap.get("count1"));
                    }
                }
                for(Map<String,String> pInvalidCountMap : pInvalidCountSelect) {
                    if (userMap.get("email").equals(pInvalidCountMap.get("REPORTER"))) {
                        kpiModel.setPinvalidBug(Integer.parseInt(pInvalidCountMap.get("invalidCount")));
                        score = score + Double.parseDouble(pInvalidCountMap.get("count1"));
                    }
                }
                kpiModel.setScore(score);
                kpiModel.setRatio((long) 1.0);
                kpiModelsList.add(kpiModel);
                //排序
                TesterKpiModel kpiModels = new TesterKpiModel();
                for(int i=0;i<kpiModelsList.size();i++){
                    for(int j=0;j<kpiModelsList.size()-1-i;j++){
                        if((kpiModelsList.get(j).getScore())<=(kpiModelsList.get(j+1).getScore())) {
                            kpiModels=kpiModelsList.get(j);
                            kpiModelsList.set(j,kpiModelsList.get(j+1));
                            kpiModelsList.set(j+1,kpiModels);
                        }
                    }
                }
                //换算得分
                for (int i=0;i<kpiModelsList.size();i++) {
                    kpiModels=kpiModelsList.get(i);
                    Double conversionScore = kpiModels.getScore()*40 / kpiModelsList.get(0).getScore();
                    String result = String .format("%.2f",conversionScore);
                    kpiModelsList.get(i).setConversionScore(result);
                }

            }
            return ResultBean.success("查询成功！", kpiModelsList);
        } catch (Exception e) {
            logger.error("sql语句有误：{}", e);
            return ResultBean.failed("sql语句有误!");
        }
    }


    /**
     * 开发bug、任务统计表
     * @param group
     * @param version
     * @param type  1为测试  0位开发
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/developer/listV2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> kpiDevelopersListV3(@RequestParam("group") String group,@RequestParam("version") String version,@RequestParam("type") Integer type,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime)  {
        logger.info("/boss/kpi/developer/list GET 方法被调用!!");
        try{
            //sql语句拼接
            String user = "";
            String versionSql = "";
            String start = "";
            String end = "";
            String ASSIGNEE = "";
            String REPORTER = "";
            if(!StringUtils.isEmpty(startTime)){
                start = "and CREATED >='" + startTime + "'";
            }
            if(!StringUtils.isEmpty(endTime)){
                end = " and CREATED <='" + endTime + "'";
            }
            if(!StringUtils.isEmpty(group)){
                user = "select * from boss_tools_jira_user_tb where grouping='" + group + "'";
            }else if(StringUtils.isEmpty(group) && type.equals(1)) {
                user = "select * from boss_tools_jira_user_tb where identity = 'qa' ";
            }else if(StringUtils.isEmpty(group) && type.equals(0)) {
                user = "select * from boss_tools_jira_user_tb where identity = 'develop' ";
            }else{
                return ResultBean.failed("传入参数有误！", "");
            }
            if(type.equals(1)){
                ASSIGNEE = "REPORTER";
                REPORTER = "ASSIGNEE";
            }else if(type.equals(0)){
                ASSIGNEE = "ASSIGNEE";
                REPORTER = "REPORTER";
            }
            if(!StringUtils.isEmpty(version)) {
                versionSql = "and id in (select SOURCE_NODE_ID from nodeassociation where SINK_NODE_ID in (select id from projectversion where vname='"+version+"') and  ASSOCIATION_TYPE='IssueFixVersion')";
            }
            List<TesterKpiModel> kpiModelsList = new ArrayList<>();
            ConnectionDB twldb = new ConnectionDB(DataField.jdbc_driver,DataField.twl_url,DataField.username,DataField.password);
            ConnectionDB db = new ConnectionDB(DataField.jdbc_driver,DataField.jira_url,DataField.jiraUsername,DataField.jiraPassword);
            List<Map<String,String>> userSelect = twldb.select(user);
            if(userSelect ==null || userSelect.size() == 0){
                return ResultBean.failed("没有要查询的数据！", "");
            }
            String username = "";
            //拼接所有人
            for (Map<String,String> userMap : userSelect) {
                username = username + "'" +userMap.get("email") + "',";
            }
            if(!StringUtils.isEmpty(username)){
                username = username.substring(0, username.length()-1);
            }
            //bug总数
            String bugCount = "select count(*) totalCount ,"+ASSIGNEE+"  from jiraissue WHERE "+ASSIGNEE+" in ("+ username +")" + start  + end + versionSql +" group by "+ASSIGNEE;
            List<Map<String,String>> bugCountSelect = db.select(bugCount);
            List<Map<String,String>> effectiveBugCountSelect=null;
            if(type.equals(0)){
                //有效缺陷总数(只针对开发)
                String effectiveBugCount = "select count(*) totalCount ,"+ASSIGNEE+"  from jiraissue where id in (select id  from jiraissue WHERE issuetype =1 and (RESOLUTION NOT IN(3,4,5) OR RESOLUTION IS NULL))  and "+ASSIGNEE+" in ("+ username +")" + start  + end + versionSql +" group by "+ASSIGNEE;
                effectiveBugCountSelect = db.select(effectiveBugCount);
            }
            //任务总数
            String taskCount = "select count(*) taskCount, count(*) *(50) count1, "+REPORTER+" from jiraissue where PROJECT='11205' and "+REPORTER+" in ("+ username +") " +  start  + end + " group by "+REPORTER;
            List<Map<String,String>> taskCountSelect = db.select(taskCount);
            //p1数
            String pOneSelect = "select count(*) p1Count,count(*) *(25) count1 ,"+ASSIGNEE+" from jiraissue where PRIORITY='1' and RESOLUTION not in ('3','4','5') and  "+ASSIGNEE+" in ("+ username +") " +  start  + end +versionSql+ " group by "+ASSIGNEE;
            List<Map<String,String>> pOneCountSelect = db.select(pOneSelect);
            //p2数
            String pTwoSelect = "select count(*) p2Count,count(*) *(20) count1,"+ASSIGNEE+" from jiraissue where PRIORITY='2' and RESOLUTION not in ('3','4','5') and "+ASSIGNEE+" in ("+ username +") " +  start  + end +versionSql+ " group by "+ASSIGNEE;
            List<Map<String,String>> pTwoCountSelect = db.select(pTwoSelect);
            //p3数
            String pThreeSelect = "select count(*) p3Count,count(*) *(15) count1,"+ASSIGNEE+" from jiraissue where PRIORITY='3' and RESOLUTION not in ('3','4','5') and "+ASSIGNEE+" in ("+ username +") " +  start  + end +versionSql+ " group by "+ASSIGNEE;
            List<Map<String,String>> pThreeCountSelect = db.select(pThreeSelect);
            //p4数
            String pFourSelect = "select count(*) p4Count,count(*) *(10) count1,"+ASSIGNEE+" from jiraissue where PRIORITY='4' and RESOLUTION not in ('3','4','5')  and "+ASSIGNEE+" in ("+ username +") " +  start  + end +versionSql+ " group by "+ASSIGNEE;
            List<Map<String,String>> pFourCountSelect = db.select(pFourSelect);
            //p5数
            String pFiveSelect = "select count(*) p5Count,count(*) *(5) count1,"+ASSIGNEE+" from jiraissue where PRIORITY='5' and RESOLUTION not in ('3','4','5') and "+ASSIGNEE+" in ("+ username +") " +  start  + end + versionSql+ " group by "+ASSIGNEE;
            List<Map<String,String>> pFiveCountSelect = db.select(pFiveSelect);
            //未解决数
            String pNoSelect = "select count(*) openCount,count(*) *(10) count1,"+ASSIGNEE+" from jiraissue where  RESOLUTION IS NULL and "+ASSIGNEE+" in ("+ username +")" +  start  + end +versionSql+ " group by "+ASSIGNEE;
            List<Map<String,String>> pNoCountSelect = db.select(pNoSelect);
            //无效数
            String pInvalidSelect = "select count(*) invalidCount,count(*) *(-15) count1,"+ASSIGNEE+" from jiraissue where RESOLUTION in ('3','4','5')  and "+ASSIGNEE+" in ("+ username +")" +  start  + end +versionSql+ " group by "+ASSIGNEE;
            List<Map<String,String>> pInvalidCountSelect = db.select(pInvalidSelect);

            //用于计算总和的model
            TesterKpiModel kpiModelSum = new TesterKpiModel();
            int bugCountSum = 0;
            int effectiveBugCountSum = 0;
            int taskCountSum = 0;
            int PoneBugSum = 0;
            int PtwoBugSum = 0;
            int PthreeBugSum = 0;
            int PfourBugSum = 0;
            int PfiveBugSum = 0;
            int PnoBugSum = 0;
            int PinvalidBugSum = 0;

            for(Map<String,String> userMap : userSelect){
                TesterKpiModel kpiModel = new TesterKpiModel();
                kpiModel.setEmail(userMap.get("email"));
                kpiModel.setName(userMap.get("name"));
                //添加bug数
                for(Map<String,String> bugCountMap : bugCountSelect) {
                    if (bugCountMap.get(ASSIGNEE).equals(userMap.get("email"))) {
                        kpiModel.setBugCount(Integer.parseInt(bugCountMap.get("totalCount")));
                        bugCountSum = bugCountSum + Integer.parseInt(bugCountMap.get("totalCount"));
                    }
                }
                if(type.equals(0)){
                    //有效缺陷总数
                    for(Map<String,String> effectiveBugMap : effectiveBugCountSelect) {
                        if (effectiveBugMap.get(ASSIGNEE).equals(userMap.get("email"))) {
                            kpiModel.setEffectiveBugCount(Integer.parseInt(effectiveBugMap.get("totalCount")));
                            effectiveBugCountSum = effectiveBugCountSum + Integer.parseInt(effectiveBugMap.get("totalCount"));
                        }
                    }
                }
                //添加任务总数
                for(Map<String,String> taskCountMap : taskCountSelect){
                    if(userMap.get("email").equals(taskCountMap.get(REPORTER))){
                        kpiModel.setTaskCount(Integer.parseInt(taskCountMap.get("taskCount")));
                        taskCountSum = taskCountSum + Integer.parseInt(taskCountMap.get("taskCount"));
                    }
                }
                for(Map<String,String> pOneCountMap : pOneCountSelect) {
                    if (userMap.get("email").equals(pOneCountMap.get(ASSIGNEE))) {
                        kpiModel.setPoneBug(Integer.parseInt(pOneCountMap.get("p1Count")));
                        PoneBugSum = PoneBugSum + Integer.parseInt(pOneCountMap.get("p1Count"));
                    }
                }
                for(Map<String,String> pTwoCountMap : pTwoCountSelect) {
                    if (userMap.get("email").equals(pTwoCountMap.get(ASSIGNEE))) {
                        kpiModel.setPtwoBug(Integer.parseInt(pTwoCountMap.get("p2Count")));
                        PtwoBugSum = PtwoBugSum + Integer.parseInt(pTwoCountMap.get("p2Count"));
                    }
                }
                for(Map<String,String> pThreeCountMap : pThreeCountSelect) {
                    if (userMap.get("email").equals(pThreeCountMap.get(ASSIGNEE))) {
                        kpiModel.setPthreeBug(Integer.parseInt(pThreeCountMap.get("p3Count")));
                        PthreeBugSum = PthreeBugSum + Integer.parseInt(pThreeCountMap.get("p3Count"));
                    }
                }
                for(Map<String,String> pFourCountMap : pFourCountSelect) {
                    if (userMap.get("email").equals(pFourCountMap.get(ASSIGNEE))) {
                        kpiModel.setPfourBug(Integer.parseInt(pFourCountMap.get("p4Count")));
                        PfourBugSum = PfourBugSum + Integer.parseInt(pFourCountMap.get("p4Count"));
                    }
                }
                for(Map<String,String> pFiveCountMap : pFiveCountSelect) {
                    if (userMap.get("email").equals(pFiveCountMap.get(ASSIGNEE))) {
                        kpiModel.setPfiveBug(Integer.parseInt(pFiveCountMap.get("p5Count")));
                        PfiveBugSum = PfiveBugSum + Integer.parseInt(pFiveCountMap.get("p5Count"));
                    }
                }
                for(Map<String,String> pNoCountMap : pNoCountSelect) {
                    if (userMap.get("email").equals(pNoCountMap.get(ASSIGNEE))) {
                        kpiModel.setPnoBug(Integer.parseInt(pNoCountMap.get("openCount")));
                        PnoBugSum = PnoBugSum + Integer.parseInt(pNoCountMap.get("openCount"));
                    }
                }
                for(Map<String,String> pInvalidCountMap : pInvalidCountSelect) {
                    if (userMap.get("email").equals(pInvalidCountMap.get(ASSIGNEE))) {
                        kpiModel.setPinvalidBug(Integer.parseInt(pInvalidCountMap.get("invalidCount")));
                        PinvalidBugSum = PinvalidBugSum + Integer.parseInt(pInvalidCountMap.get("invalidCount"));
                    }
                }
                kpiModelsList.add(kpiModel);


                //排序
                TesterKpiModel kpiModels = new TesterKpiModel();
                for(int i=0;i<kpiModelsList.size();i++){
                    for(int j=0;j<kpiModelsList.size()-1-i;j++){
                        if((kpiModelsList.get(j).getBugCount())<=(kpiModelsList.get(j+1).getBugCount())) {
                            kpiModels=kpiModelsList.get(j);
                            kpiModelsList.set(j,kpiModelsList.get(j+1));
                            kpiModelsList.set(j+1,kpiModels);
                        }
                    }
                }
            }
            //每项求和
            kpiModelSum.setBugCount(bugCountSum);
            kpiModelSum.setEffectiveBugCount(effectiveBugCountSum);
            kpiModelSum.setTaskCount(taskCountSum);
            kpiModelSum.setPoneBug(PoneBugSum);
            kpiModelSum.setPtwoBug(PtwoBugSum);
            kpiModelSum.setPthreeBug(PthreeBugSum);
            kpiModelSum.setPfourBug(PfourBugSum);
            kpiModelSum.setPfiveBug(PfiveBugSum);
            kpiModelSum.setPnoBug(PnoBugSum);
            kpiModelSum.setPinvalidBug(PinvalidBugSum);
            kpiModelSum.setName("总和");
            kpiModelsList.add(kpiModelSum);

            return ResultBean.success("查询成功！", kpiModelsList);

        } catch (Exception e) {
            logger.error("sql语句有误：{}", e);
            return ResultBean.failed("sql语句有误!");
        }
    }




    /**
     * boss反馈bug分布统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/feedback/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> kpiDevelopersListV3(@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime)  {
        logger.info("/feedback/list GET 方法被调用!!");
        try{
            List<TesterKpiModel> kpiModelsList = new ArrayList<>();
            //sql语句拼接
            String start = "";
            String end = "";
            if(!StringUtils.isEmpty(startTime)){
                start = "and CREATED >='" + startTime + "'";
            }
            if(!StringUtils.isEmpty(endTime)){
                end = " and CREATED <='" + endTime + "'";
            }
            ConnectionDB db = new ConnectionDB(DataField.jdbc_driver,DataField.jira_url,DataField.jiraUsername,DataField.jiraPassword);
            String publicSqlOne = "select count(*) totalCount ,b.SINK_NODE_ID,b.cname from  ";
            String publicSqlTwo = " as a,(select c.SOURCE_NODE_ID,c.SINK_NODE_ID,d.cname from nodeassociation as c,component as d where c.ASSOCIATION_TYPE='IssueComponent' and d.project='11208' and c.SINK_NODE_ID = d.ID) AS b where a.id = b.SOURCE_NODE_ID GROUP BY b.SINK_NODE_ID ";
            List<Map<String,String>> bugCountSelect = db.select(publicSqlOne+" (select * from jiraissue where 1=1 "+start  + end +")" + publicSqlTwo);

            //有效缺陷总数(只针对开发)
            String effectiveBugCount = publicSqlOne + " (select * from jiraissue where id in (select id  from jiraissue WHERE issuetype =1 and (RESOLUTION NOT IN(3,4,5) OR RESOLUTION IS NULL)) "+ start  + end+")" + publicSqlTwo;
            List<Map<String,String>> effectiveBugCountSelect  = db.select(effectiveBugCount);

            //p1数
            String pOneSelect = publicSqlOne + " (select * from jiraissue where PRIORITY='1' and RESOLUTION not in ('3','4','5') "+ start  + end+")" + publicSqlTwo;
            List<Map<String,String>> pOneCountSelect = db.select(pOneSelect);
            //p2数
            String pTwoSelect = publicSqlOne + " (select * from jiraissue where PRIORITY='2' and RESOLUTION not in ('3','4','5') "+ start  + end+")" + publicSqlTwo;
            List<Map<String,String>> pTwoCountSelect = db.select(pTwoSelect);
            //p3数
            String pThreeSelect = publicSqlOne + " (select * from jiraissue where PRIORITY='3' and RESOLUTION not in ('3','4','5') "+ start  + end+")" + publicSqlTwo;
            List<Map<String,String>> pThreeCountSelect = db.select(pThreeSelect);
            //p4数
            String pFourSelect = publicSqlOne + " (select * from jiraissue where PRIORITY='4' and RESOLUTION not in ('3','4','5') " + start  + end+")" + publicSqlTwo;
            List<Map<String,String>> pFourCountSelect = db.select(pFourSelect);
            //p5数
            String pFiveSelect = publicSqlOne + " (select * from jiraissue where PRIORITY='5' and RESOLUTION not in ('3','4','5') " + start  + end+")" + publicSqlTwo;
            List<Map<String,String>> pFiveCountSelect = db.select(pFiveSelect);
            //未解决数
            String pNoSelect = publicSqlOne + " (select * from jiraissue where RESOLUTION IS NULL "+ start  + end+") " + publicSqlTwo;
            List<Map<String,String>> pNoCountSelect = db.select(pNoSelect);
            //无效数
            String pInvalidSelect = publicSqlOne + " (select * from jiraissue where RESOLUTION in ('3','4','5') "+start  + end+") " + publicSqlTwo;
            List<Map<String,String>> pInvalidCountSelect = db.select(pInvalidSelect);
            //用于计算总和的model
            TesterKpiModel kpiModelSum = new TesterKpiModel();
            int bugCountSum = 0;
            int effectiveBugCountSum = 0;
            int PoneBugSum = 0;
            int PtwoBugSum = 0;
            int PthreeBugSum = 0;
            int PfourBugSum = 0;
            int PfiveBugSum = 0;
            int PnoBugSum = 0;
            int PinvalidBugSum = 0;

            for(Map<String,String> bugCountMap : bugCountSelect){
                TesterKpiModel kpiModel = new TesterKpiModel();
                kpiModel.setCname(bugCountMap.get("cname"));
                kpiModel.setBugCount(Integer.parseInt(bugCountMap.get("totalCount")));
                bugCountSum = bugCountSum + Integer.parseInt(bugCountMap.get("totalCount"));

                for(Map<String,String> effectiveBugCountMap : effectiveBugCountSelect) {
                    if (bugCountMap.get("cname").equals(effectiveBugCountMap.get("cname"))) {
                        kpiModel.setEffectiveBugCount(Integer.parseInt(effectiveBugCountMap.get("totalCount")));
                        effectiveBugCountSum = effectiveBugCountSum + Integer.parseInt(effectiveBugCountMap.get("totalCount"));
                    }
                }
                for(Map<String,String> pOneCountMap : pOneCountSelect) {
                    if (bugCountMap.get("cname").equals(pOneCountMap.get("cname"))) {
                        kpiModel.setPoneBug(Integer.parseInt(pOneCountMap.get("totalCount")));
                        PoneBugSum = PoneBugSum + Integer.parseInt(pOneCountMap.get("totalCount"));
                    }
                }
                for(Map<String,String> pTwoCountMap : pTwoCountSelect) {
                    if (bugCountMap.get("cname").equals(pTwoCountMap.get("cname"))) {
                        kpiModel.setPtwoBug(Integer.parseInt(pTwoCountMap.get("totalCount")));
                        PtwoBugSum = PtwoBugSum + Integer.parseInt(pTwoCountMap.get("totalCount"));
                    }
                }
                for(Map<String,String> pThreeCountMap : pThreeCountSelect) {
                    if (bugCountMap.get("cname").equals(pThreeCountMap.get("cname"))) {
                        kpiModel.setPthreeBug(Integer.parseInt(pThreeCountMap.get("totalCount")));
                        PthreeBugSum = PthreeBugSum + Integer.parseInt(pThreeCountMap.get("totalCount"));
                    }
                }
                for(Map<String,String> pfourCountMap : pFourCountSelect) {
                    if (bugCountMap.get("cname").equals(pfourCountMap.get("cname"))) {
                        kpiModel.setPfourBug(Integer.parseInt(pfourCountMap.get("totalCount")));
                        PfourBugSum = PfourBugSum + Integer.parseInt(pfourCountMap.get("totalCount"));
                    }
                }
                for(Map<String,String> pFiveCountMap : pFiveCountSelect) {
                    if (bugCountMap.get("cname").equals(pFiveCountMap.get("cname"))) {
                        kpiModel.setPfiveBug(Integer.parseInt(pFiveCountMap.get("totalCount")));
                        PfiveBugSum = PfiveBugSum + Integer.parseInt(pFiveCountMap.get("totalCount"));
                    }
                }
                for(Map<String,String> pNoCountMap : pNoCountSelect) {
                    if (bugCountMap.get("cname").equals(pNoCountMap.get("cname"))) {
                        kpiModel.setPnoBug(Integer.parseInt(pNoCountMap.get("totalCount")));
                        PnoBugSum = PnoBugSum + Integer.parseInt(pNoCountMap.get("totalCount"));
                    }
                }
                for(Map<String,String> pInvalidCountMap : pInvalidCountSelect) {
                    if (bugCountMap.get("cname").equals(pInvalidCountMap.get("cname"))) {
                        kpiModel.setPinvalidBug(Integer.parseInt(pInvalidCountMap.get("totalCount")));
                        PinvalidBugSum = PinvalidBugSum + Integer.parseInt(pInvalidCountMap.get("totalCount"));
                    }
                }
                kpiModelsList.add(kpiModel);
            }
            //排序
            TesterKpiModel kpiModels = new TesterKpiModel();
            for(int i=0;i<kpiModelsList.size();i++){
                for(int j=0;j<kpiModelsList.size()-1-i;j++){
                    if((kpiModelsList.get(j).getBugCount())<=(kpiModelsList.get(j+1).getBugCount())) {
                        kpiModels=kpiModelsList.get(j);
                        kpiModelsList.set(j,kpiModelsList.get(j+1));
                        kpiModelsList.set(j+1,kpiModels);
                    }
                }
            }
            //每项求和
            kpiModelSum.setBugCount(bugCountSum);
            kpiModelSum.setEffectiveBugCount(effectiveBugCountSum);
            kpiModelSum.setPoneBug(PoneBugSum);
            kpiModelSum.setPtwoBug(PtwoBugSum);
            kpiModelSum.setPthreeBug(PthreeBugSum);
            kpiModelSum.setPfourBug(PfourBugSum);
            kpiModelSum.setPfiveBug(PfiveBugSum);
            kpiModelSum.setPnoBug(PnoBugSum);
            kpiModelSum.setPinvalidBug(PinvalidBugSum);
            kpiModelSum.setCname("总和");
            kpiModelsList.add(kpiModelSum);
            return ResultBean.success("查询成功！",kpiModelsList);
        } catch (Exception e) {
            logger.error("sql语句有误：{}", e);
            return ResultBean.failed("sql语句有误!");
        }
    }


    /**
     * boss反馈bug分布表-开发组
     * @param group
     * @param version
     * @param type  1为测试  0位开发
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/developer/listV3", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> kpiDevelopersListV4(@RequestParam("group") String group,@RequestParam("version") String version,@RequestParam("type") Integer type,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime)  {
        logger.info("/boss/kpi/developer/list GET 方法被调用!!");
        try{
            //sql语句拼接
            String user = "";
            String versionSql = "";
            String start = "";
            String end = "";
            String ASSIGNEE = "";
            String REPORTER = "";
            //起止时间
            if(!StringUtils.isEmpty(startTime)){
                start = "and CREATED >='" + startTime + "'";
            }
            if(!StringUtils.isEmpty(endTime)){
                end = " and CREATED <='" + endTime + "'";
            }
            //分组人员
            if(!StringUtils.isEmpty(group)){
                user = "select * from boss_tools_jira_user_tb where grouping='" + group + "'";
            }else if(StringUtils.isEmpty(group) && type.equals(1)) {
                user = "select * from boss_tools_jira_user_tb where identity = 'qa' ";
            }else if(StringUtils.isEmpty(group) && type.equals(0)) {
                user = "select * from boss_tools_jira_user_tb where identity = 'develop' ";
            }else{
                return ResultBean.failed("传入参数有误！", "");
            }
            //查经办人还是报告人
            if(type.equals(1)){
                ASSIGNEE = "REPORTER";
                REPORTER = "ASSIGNEE";
            }else if(type.equals(0)){
                ASSIGNEE = "ASSIGNEE";
                REPORTER = "REPORTER";
            }

            if(!StringUtils.isEmpty(version)) {
                versionSql = "and id in (select SOURCE_NODE_ID from nodeassociation where SINK_NODE_ID in (select id from projectversion where vname='"+version+"') and  ASSOCIATION_TYPE='IssueFixVersion')";
            }
            List<TesterKpiModel> kpiModelsList = new ArrayList<>();
            ConnectionDB twldb = new ConnectionDB(DataField.jdbc_driver,DataField.twl_url,DataField.username,DataField.password);
            ConnectionDB db = new ConnectionDB(DataField.jdbc_driver,DataField.jira_url,DataField.jiraUsername,DataField.jiraPassword);
            List<Map<String,String>> userSelect = twldb.select(user);
            if(userSelect ==null || userSelect.size() == 0){
                return ResultBean.failed("没有要查询的数据！", "");
            }
            String username = "";
            //拼接所有人
            for (Map<String,String> userMap : userSelect) {
                username = username + "'" +userMap.get("email") + "',";
            }
            if(!StringUtils.isEmpty(username)){
                username = username.substring(0, username.length()-1);
            }
            //bug总数
            String bugCount = "select count(*) totalCount ,"+ASSIGNEE+"  from jiraissue WHERE "+ASSIGNEE+" in ("+ username +")" + start  + end + versionSql +"and project='11208'"+" group by "+ASSIGNEE;
            List<Map<String,String>> bugCountSelect = db.select(bugCount);
            List<Map<String,String>> effectiveBugCountSelect=null;
            if(type.equals(0)){
                //有效缺陷总数(只针对开发)
                String effectiveBugCount = "select count(*) totalCount ,"+ASSIGNEE+"  from jiraissue where id in (select id  from jiraissue WHERE issuetype =1 and (RESOLUTION NOT IN(3,4,5) OR RESOLUTION IS NULL))  and "+ASSIGNEE+" in ("+ username +")" + start  + end + versionSql+"and project='11208'" +" group by "+ASSIGNEE;
                effectiveBugCountSelect = db.select(effectiveBugCount);
            }
            //任务总数
            String taskCount = "select count(*) taskCount, count(*) *(50) count1, "+REPORTER+" from jiraissue where PROJECT='11205' and "+REPORTER+" in ("+ username +") " +  start  + end +"and project='11208'"+ " group by "+REPORTER;
            List<Map<String,String>> taskCountSelect = db.select(taskCount);
            //p1数
            String pOneSelect = "select count(*) p1Count,count(*) *(25) count1 ,"+ASSIGNEE+" from jiraissue where PRIORITY='1' and RESOLUTION not in ('3','4','5') and  "+ASSIGNEE+" in ("+ username +") " +  start  + end +versionSql+"and project='11208'"+ " group by "+ASSIGNEE;
            List<Map<String,String>> pOneCountSelect = db.select(pOneSelect);
            //p2数
            String pTwoSelect = "select count(*) p2Count,count(*) *(20) count1,"+ASSIGNEE+" from jiraissue where PRIORITY='2' and RESOLUTION not in ('3','4','5') and "+ASSIGNEE+" in ("+ username +") " +  start  + end +versionSql+"and project='11208'"+ " group by "+ASSIGNEE;
            List<Map<String,String>> pTwoCountSelect = db.select(pTwoSelect);
            //p3数
            String pThreeSelect = "select count(*) p3Count,count(*) *(15) count1,"+ASSIGNEE+" from jiraissue where PRIORITY='3' and RESOLUTION not in ('3','4','5') and "+ASSIGNEE+" in ("+ username +") " +  start  + end +versionSql+"and project='11208'"+ " group by "+ASSIGNEE;
            List<Map<String,String>> pThreeCountSelect = db.select(pThreeSelect);
            //p4数
            String pFourSelect = "select count(*) p4Count,count(*) *(10) count1,"+ASSIGNEE+" from jiraissue where PRIORITY='4' and RESOLUTION not in ('3','4','5')  and "+ASSIGNEE+" in ("+ username +") " +  start  + end +versionSql+"and project='11208'"+ " group by "+ASSIGNEE;
            List<Map<String,String>> pFourCountSelect = db.select(pFourSelect);
            //p5数
            String pFiveSelect = "select count(*) p5Count,count(*) *(5) count1,"+ASSIGNEE+" from jiraissue where PRIORITY='5' and RESOLUTION not in ('3','4','5') and "+ASSIGNEE+" in ("+ username +") " +  start  + end + versionSql+"and project='11208'"+ " group by "+ASSIGNEE;
            List<Map<String,String>> pFiveCountSelect = db.select(pFiveSelect);
            //未解决数
            String pNoSelect = "select count(*) openCount,count(*) *(10) count1,"+ASSIGNEE+" from jiraissue where  RESOLUTION IS NULL and "+ASSIGNEE+" in ("+ username +")" +  start  + end +versionSql+"and project='11208'"+ " group by "+ASSIGNEE;
            List<Map<String,String>> pNoCountSelect = db.select(pNoSelect);
            //无效数
            String pInvalidSelect = "select count(*) invalidCount,count(*) *(-15) count1,"+ASSIGNEE+" from jiraissue where RESOLUTION in ('3','4','5')  and "+ASSIGNEE+" in ("+ username +")" +  start  + end +versionSql+"and project='11208'"+ " group by "+ASSIGNEE;
            List<Map<String,String>> pInvalidCountSelect = db.select(pInvalidSelect);

            //用于计算总和的model
            TesterKpiModel kpiModelSum = new TesterKpiModel();
            int bugCountSum = 0;
            int effectiveBugCountSum = 0;
            int taskCountSum = 0;
            int PoneBugSum = 0;
            int PtwoBugSum = 0;
            int PthreeBugSum = 0;
            int PfourBugSum = 0;
            int PfiveBugSum = 0;
            int PnoBugSum = 0;
            int PinvalidBugSum = 0;

            for(Map<String,String> userMap : userSelect){
                TesterKpiModel kpiModel = new TesterKpiModel();
                kpiModel.setEmail(userMap.get("email"));
                kpiModel.setName(userMap.get("name"));
                //添加bug数
                for(Map<String,String> bugCountMap : bugCountSelect) {
                    if (bugCountMap.get(ASSIGNEE).equals(userMap.get("email"))) {
                        kpiModel.setBugCount(Integer.parseInt(bugCountMap.get("totalCount")));
                        bugCountSum = bugCountSum + Integer.parseInt(bugCountMap.get("totalCount"));
                    }
                }
                if(type.equals(0)){
                    //有效缺陷总数
                    for(Map<String,String> effectiveBugMap : effectiveBugCountSelect) {
                        if (effectiveBugMap.get(ASSIGNEE).equals(userMap.get("email"))) {
                            kpiModel.setEffectiveBugCount(Integer.parseInt(effectiveBugMap.get("totalCount")));
                            effectiveBugCountSum = effectiveBugCountSum + Integer.parseInt(effectiveBugMap.get("totalCount"));
                        }
                    }
                }
                //添加任务总数
                for(Map<String,String> taskCountMap : taskCountSelect){
                    if(userMap.get("email").equals(taskCountMap.get(REPORTER))){
                        kpiModel.setTaskCount(Integer.parseInt(taskCountMap.get("taskCount")));
                        taskCountSum = taskCountSum + Integer.parseInt(taskCountMap.get("taskCount"));
                    }
                }
                for(Map<String,String> pOneCountMap : pOneCountSelect) {
                    if (userMap.get("email").equals(pOneCountMap.get(ASSIGNEE))) {
                        kpiModel.setPoneBug(Integer.parseInt(pOneCountMap.get("p1Count")));
                        PoneBugSum = PoneBugSum + Integer.parseInt(pOneCountMap.get("p1Count"));
                    }
                }
                for(Map<String,String> pTwoCountMap : pTwoCountSelect) {
                    if (userMap.get("email").equals(pTwoCountMap.get(ASSIGNEE))) {
                        kpiModel.setPtwoBug(Integer.parseInt(pTwoCountMap.get("p2Count")));
                        PtwoBugSum = PtwoBugSum + Integer.parseInt(pTwoCountMap.get("p2Count"));
                    }
                }
                for(Map<String,String> pThreeCountMap : pThreeCountSelect) {
                    if (userMap.get("email").equals(pThreeCountMap.get(ASSIGNEE))) {
                        kpiModel.setPthreeBug(Integer.parseInt(pThreeCountMap.get("p3Count")));
                        PthreeBugSum = PthreeBugSum + Integer.parseInt(pThreeCountMap.get("p3Count"));
                    }
                }
                for(Map<String,String> pFourCountMap : pFourCountSelect) {
                    if (userMap.get("email").equals(pFourCountMap.get(ASSIGNEE))) {
                        kpiModel.setPfourBug(Integer.parseInt(pFourCountMap.get("p4Count")));
                        PfourBugSum = PfourBugSum + Integer.parseInt(pFourCountMap.get("p4Count"));
                    }
                }
                for(Map<String,String> pFiveCountMap : pFiveCountSelect) {
                    if (userMap.get("email").equals(pFiveCountMap.get(ASSIGNEE))) {
                        kpiModel.setPfiveBug(Integer.parseInt(pFiveCountMap.get("p5Count")));
                        PfiveBugSum = PfiveBugSum + Integer.parseInt(pFiveCountMap.get("p5Count"));
                    }
                }
                for(Map<String,String> pNoCountMap : pNoCountSelect) {
                    if (userMap.get("email").equals(pNoCountMap.get(ASSIGNEE))) {
                        kpiModel.setPnoBug(Integer.parseInt(pNoCountMap.get("openCount")));
                        PnoBugSum = PnoBugSum + Integer.parseInt(pNoCountMap.get("openCount"));
                    }
                }
                for(Map<String,String> pInvalidCountMap : pInvalidCountSelect) {
                    if (userMap.get("email").equals(pInvalidCountMap.get(ASSIGNEE))) {
                        kpiModel.setPinvalidBug(Integer.parseInt(pInvalidCountMap.get("invalidCount")));
                        PinvalidBugSum = PinvalidBugSum + Integer.parseInt(pInvalidCountMap.get("invalidCount"));
                    }
                }
                kpiModelsList.add(kpiModel);


                //排序
                TesterKpiModel kpiModels = new TesterKpiModel();
                for(int i=0;i<kpiModelsList.size();i++){
                    for(int j=0;j<kpiModelsList.size()-1-i;j++){
                        if((kpiModelsList.get(j).getBugCount())<=(kpiModelsList.get(j+1).getBugCount())) {
                            kpiModels=kpiModelsList.get(j);
                            kpiModelsList.set(j,kpiModelsList.get(j+1));
                            kpiModelsList.set(j+1,kpiModels);
                        }
                    }
                }
            }
            //每项求和
            kpiModelSum.setBugCount(bugCountSum);
            kpiModelSum.setEffectiveBugCount(effectiveBugCountSum);
            kpiModelSum.setTaskCount(taskCountSum);
            kpiModelSum.setPoneBug(PoneBugSum);
            kpiModelSum.setPtwoBug(PtwoBugSum);
            kpiModelSum.setPthreeBug(PthreeBugSum);
            kpiModelSum.setPfourBug(PfourBugSum);
            kpiModelSum.setPfiveBug(PfiveBugSum);
            kpiModelSum.setPnoBug(PnoBugSum);
            kpiModelSum.setPinvalidBug(PinvalidBugSum);
            kpiModelSum.setName("总和");
            kpiModelsList.add(kpiModelSum);

            return ResultBean.success("查询成功！", kpiModelsList);

        } catch (Exception e) {
            logger.error("sql语句有误：{}", e);
            return ResultBean.failed("sql语句有误!");
        }
    }



}