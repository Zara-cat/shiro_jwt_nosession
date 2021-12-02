package com.zara.enums;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : ExecutionState
 * @description : [程序执行状态]
 * @createTime : [2021/11/30 23:22]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/11/30 23:22]
 * @updateRemark : [描述说明本次修改内容]
 */

import lombok.Getter;

/**
 *1000～1999 客户端请求错误，前缀：REQ_
 * 2000～2999 用户操作错误，前缀：USER_
 * 3000～3999 业务处理错误，前缀：SYSTEM_
 */
@Getter
public enum ExecutionState {
    /**
     * 客户端请求错误
     */
    OK(1000, "成功"),
    REQ_FORMAT_ERROR(1001, "参数格式错误"),
    REQ_ILLEGAL_CHARACTER(1002, "非法字符"),
    REQ_ARGUMENT_TYPE_MISMATCH(1007, "参数类型不匹配"),
    REQ_METHOD_NOT_SUPPORT(1010,"请求方式不支持"),
    REQ_NO_TOEKN(1011, "未携带 token"),


    /**
     * 用户操作错误
     */
    USER_LOGIN_SUCCESS(2000,"登录成功！"),
    USER_UNAUTHORIZED(2001, "操作未授权"),
    USER_UNAUTHENTICATED(2002, "身份未认证"),
    USER_INVALID_TOKEN(2003, "无效 token"),
    USER_PASSWORD_ERROR(2004, "用户名与密码不匹配，请检查后重新输入！"),
    USER_ACCOUNT_NOT_FOUND(2005, "用户名不存在"),
    USER_OVERDUE_TOKEN(2006, "token 已经过期"),
    USER_AUTHORIZATION_FAIL(2007, "身份认证失败"),
    USER_TOKEN_REFRESH(2008, "token 需要刷新"),
    USER_NO_NEED_REFRESH(2009, "token 状态正常，不需要刷新"),
    USER_USERNAME_REPETITION(2010, "用户名重复"),
    USER_LOCKED(2011,"该账户已被锁定，如需解锁请联系管理员！"),
    USER_AUTHENTICATION_FAIL(2012,"登录异常，请联系管理员！"),

    /**
     * 系统错误
     */
    SYSTEM_UNKNOWN_EXCEPTION(3000, "系统未知异常"),
    SYSTEM_TIME_OUT(3002, "超时"),
    SYSTEM_ADD_ERROR(3003, "添加失败"),
    SYSTEM_UPDATE_ERROR(3004, "更新失败"),
    SYSTEM_DELETE_ERROR(3005, "删除失败"),
    SYSTEM_GET_ERROR(3006, "查找失败"),
    SYSTEM_NULL_POINTER(3007, "空指针异常"),
    SYSTEM_3008(3008, "非Web上下文无法获取Request"),
    SYSTEM_IO_EXCEPTION(3009, "IO 流操作中断"),
    SYSTEM_SHIRO_EXCEPTION(3010, "权限管理器异常"),
    SYSTEM_ACCOUNT_REPETITION(3011, "账户重复"),
    SYSTEM_TARGET_NOT_EXIST(3012, "目标不存在"),
    ;

    private final Integer code;

    private final String msg;

    ExecutionState(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通过状态码获取枚举对象
     * @param code 状态码
     * @return 枚举对象
     */
    public static ExecutionState getMessage(int code){
        for (ExecutionState executionState : ExecutionState.values()) {
            if(code == executionState.getCode()){
                return executionState;
            }
        }
        return null;
    }
}
