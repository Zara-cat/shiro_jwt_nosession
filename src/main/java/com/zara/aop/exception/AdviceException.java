package com.zara.aop.exception;

import com.zara.utils.response.Responder;
import com.zara.utils.response.ResultDTO;
import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : AdviceException
 * @description : [处理全局异常]
 * @createTime : [2021/12/1 15:13]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 15:13]
 * @updateRemark : [描述说明本次修改内容]
 */

@RestControllerAdvice
public class AdviceException {
    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public ResultDTO<Object> handleShiroException(ShiroException e) {
        return Responder.failure(401,e.getMessage());
    }
    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public Object globalException(HttpServletRequest request, Throwable ex) {
        return Responder.failure(401,ex.getMessage());
    }
}
