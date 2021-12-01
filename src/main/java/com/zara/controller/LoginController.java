package com.zara.controller;

import com.zara.enums.ExecutionState;
import com.zara.jwt.JwtUtils;
import com.zara.utils.response.Responder;
import com.zara.utils.response.ResultDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : LoginController
 * @description : [认证控制器]
 * @createTime : [2021/11/30 17:06]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/11/30 17:06]
 * @updateRemark : [描述说明本次修改内容]
 */
@RestController
public class LoginController {

    /**
     * 登录认证
     * @param userName 账号
     * @param password 密码
     * @return {@link ResultDTO}
     */
    @PostMapping(value = "/login")
    public ResultDTO<Object> userLogin(@RequestParam(name = "username",required = true) String userName,
                                       @RequestParam(name = "password",required = true) String password,ServletResponse response){
        //获得当前用户主体
        Subject subject = SecurityUtils.getSubject();
        //将用户名和密码封装成 UsernamePasswordToken 对象
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
        try {
            subject.login(usernamePasswordToken);
            //登录成功，签发 JWT token
            String jwtToken = JwtUtils.sign(userName, JwtUtils.SECRET);
            //将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
            ((HttpServletResponse) response).setHeader(JwtUtils.AUTH_HEADER,jwtToken);
            return Responder.successful();
        } catch (UnknownAccountException e) { //账号不存在
            return Responder.failure(ExecutionState.USER_ACCOUNT_NOT_FOUND);
        }catch (IncorrectCredentialsException e){ //账号与密码不匹配
            return Responder.failure(ExecutionState.USER_PASSWORD_ERROR);
        }catch (LockedAccountException e){ //账号已被锁定
            return Responder.failure(ExecutionState.USER_LOCKED);
        }catch (AuthenticationException e){ //其他身份验证异常
            return Responder.failure(ExecutionState.USER_AUTHENTICATION_FAIL);
        }
    }


    /**
     * 退出登录
     * @return {@link ResultDTO}
     */
    @GetMapping(value = "/logout")
    public ResultDTO<Object> logout(){
        return Responder.successful("退出登录成功",null);
    }

}
