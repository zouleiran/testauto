/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package dubbo.dto;

import org.apache.commons.lang.StringUtils;


public class ResultDTO<T> {

    private static final long serialVersionUID = 1789151585L;
    private static final int DEFAULT_EXCEPTION_CODE = -1; // 默认异常码
    private static final int DEFAULT_SUCCESS_CODE = 1; // 默认成功吗
    private static final int DEFAULT_ERROR_CODE = 0; // 默认错误吗

    private int code; // 响应码
    private boolean success; // 执行结果标识
    private String msg; // 消息
    private String remark; // 备注
    private T data; // 附带数据
    private Throwable exception; // 异常

    public static <T> ResultDTO<T> handleSuccess(String msg, T data) {

        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_SUCCESS_CODE);
        ret.setSuccess(true);
        ret.setMsg(msg);
        ret.setRemark("success");
        ret.setData(data);
        ret.setException(null);
        return ret;

    }
    public static <T> ResultDTO<T> handleError(String msg, T data) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_ERROR_CODE);
        ret.setMsg(msg);
        ret.setSuccess(false);
        ret.setRemark("occur an error");
        ret.setData(data);
        ret.setException(null);
        return ret;
    }
    public static <T> ResultDTO<T> handleException(String msg, T data, Throwable e) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_EXCEPTION_CODE);
        ret.setSuccess(false);
        ret.setMsg(null == msg ? e.getMessage() : msg);
        ret.setRemark("occur an exception");
        ret.setData(data);
        ret.setException(e);
        return ret;

    }

    public static <T> ResultDTO<T> createExceptionResult(Throwable e, Class<T> clazz) {

        return createExceptionResult("请求异常！", e, clazz);
    }

    public static <T> ResultDTO<T> createExceptionResult(String msg, Throwable e, Class<T> clazz) {

        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_EXCEPTION_CODE);
        ret.setSuccess(false);
        ret.setMsg(StringUtils.isBlank(msg) ? e.getMessage() : msg);
        ret.setRemark("occur an exception");
        ret.setData(null);
        ret.setException(e);
        return ret;
    }

    public static <T> ResultDTO<T> createErrorResult(String msg, Class<T> clazz) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_ERROR_CODE);
        ret.setMsg(msg);
        ret.setSuccess(false);
        ret.setRemark("occur an error");
        ret.setData(null);
        ret.setException(null);
        return ret;
    }

    public static <T> ResultDTO<T> createSuccessResult(String msg, Class<T> clazz) {
       return createSuccessResult(msg, null, clazz);
    }

    public static <T> ResultDTO<T> createSuccessResult(String msg, T data, Class<T> clazz) {
        ResultDTO<T> ret = new ResultDTO<>();
        ret.setCode(DEFAULT_SUCCESS_CODE);
        ret.setSuccess(true);
        ret.setMsg(msg);
        ret.setRemark("success");
        ret.setData(data);
        ret.setException(null);
        return ret;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
