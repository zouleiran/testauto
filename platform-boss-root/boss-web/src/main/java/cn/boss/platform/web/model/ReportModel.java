package cn.boss.platform.web.model;

import java.util.Date;

/**
 * Created by admin on 2019/4/17.
 */
public class ReportModel {

    private Integer id;
    private Integer projectId;
    private Integer groupId;
    private Integer interfaceId;
    private Integer envId;
    private Integer serialId;
    private Integer caseId;
    private Integer taskId;
    private Integer batchId;

    private String url;

    private String responseResult;
    private int responseStatus;
    private String parameters;
    private int expectedStatus;
    private String expectedResult;

    private Date createTime;
    private long execTime;
    private boolean result;
    private String author;

    private String path;

}
