package com.zara.utils.response;

import com.zara.enums.ExecutionState;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : Responder
 * @description : [响应工具栏]
 * @createTime : [2021/11/30 23:02]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/11/30 23:02]
 * @updateRemark : [描述说明本次修改内容]
 */
public class Responder {
    private Responder() {}

    /**
     * 成功
     * @param object 需要返回的数据
     * @return data
     */
    public static <T> ResultDTO<T> successful(T object) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(ExecutionState.OK.getCode());
        result.setMessage(ExecutionState.OK.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 成功
     * @return 返回空
     */
    public static <T> ResultDTO<T> successful() {
        return successful(null);
    }
    /**
     * 成功
     * @return 返回空
     */
    public static <T> ResultDTO<T> successful(String msg,T object) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(ExecutionState.OK.getCode());
        result.setMessage(msg);
        result.setData(object);
        return result;
    }
    /**
     * 错误
     * @param executionState 错误枚举类
     * @return 错误信息
     */
    public static ResultDTO<Object> failure(ExecutionState executionState) {
        ResultDTO<Object> result = new ResultDTO<>();
        result.setCode(executionState.getCode());
        result.setMessage(executionState.getMsg());
        return result;
    }

    /**
     * 错误
     * @param code 状态码
     * @param msg 消息
     * @return ResultBean
     */
    public static <T> ResultDTO<T> failure(Integer code, String msg) {
        ResultDTO<T> result = new ResultDTO<T>();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    /**
     * 错误
     * @param msg 错误信息
     * @return ResultBean
     */
    public static <T> ResultDTO<T> failure(String msg) {
        return failure(-1, msg);
    }
}
