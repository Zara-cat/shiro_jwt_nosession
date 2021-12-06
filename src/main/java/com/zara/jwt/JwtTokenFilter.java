package com.zara.jwt;

import com.zara.constant.SysConstant;
import com.zara.enums.ExecutionState;
import com.zara.utils.redis.RedisUtil;
import com.zara.utils.response.HttpResponseUtil;
import com.zara.utils.response.Responder;
import com.zara.utils.spring.SpringContextUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : JwtTokenFilter
 * @description : [自定义的认证过滤器，用来拦截Header中携带 JWT token的请求]
 * @createTime : [2021/12/3 15:27]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/3 15:27]
 * @updateRemark : [描述说明本次修改内容]
 */
public class JwtTokenFilter extends AccessControlFilter {
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //在访问过来的时候检测是否为OPTIONS请求，如果是就直接返回true
        if (WebUtils.toHttp(request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(servletResponse);

        if (ObjectUtils.isEmpty(httpServletRequest.getHeader(JwtUtils.AUTH_HEADER))){
            //未携带token
            HttpResponseUtil.sendJson(httpServletResponse, Responder.failure(ExecutionState.REQ_NO_TOEKN));
            //拒绝访问
            return false;
        }
        JwtToken token = new JwtToken(httpServletRequest.getHeader(JwtUtils.AUTH_HEADER));
        try {
            getSubject(servletRequest, servletResponse).login(token);
        } catch (AuthenticationException e) {
            if (e instanceof AccountException){
                //token 参数异常
                HttpResponseUtil.sendJson(httpServletResponse, Responder.failure(ExecutionState.USER_INVALID_TOKEN));
            } else if (e instanceof UnknownAccountException){
                //系统用户退出，请重新登录
                HttpResponseUtil.sendJson(httpServletResponse, Responder.failure(ExecutionState.USER_LOGOUTTED));
            }else if (e instanceof ExpiredCredentialsException){
                //token 已经过期，需要刷新
                System.out.println("token 已经过期，需要刷新");
                String result = refreshToken(servletRequest, servletResponse);
                if (result.equals("success")){
                    System.out.println("token 刷新成功");
                    return true;
                }else {
                    HttpResponseUtil.sendJson(httpServletResponse, Responder.failure(401,result));
                }
            }else if (e instanceof IncorrectCredentialsException){
                // 无效token
                HttpResponseUtil.sendJson(httpServletResponse, Responder.failure(ExecutionState.USER_INVALID_TOKEN));
            }
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    /**
     * 刷新 Token
     * @param request
     * @param response
     * @return success 成功  / error message
     */
    private String  refreshToken(ServletRequest request,ServletResponse response){
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        RedisUtil redisUtil = (RedisUtil) SpringContextUtil.getBean("redisUtil");
        //获取 token 里面的用户名
        String username = JwtUtils.getClaimFiled(httpServletRequest.getHeader(JwtUtils.AUTH_HEADER).toString(), "username");
        long currentTimeMillis = System.currentTimeMillis();
        //更新 refreshToken
        redisUtil.set(SysConstant.REFRESHTOKEN_PRE+username,currentTimeMillis,SysConstant.REFRESHTOKEN_TIME);
        //生成 token
        String token = JwtUtils.sign(username, JwtUtils.SECRET, currentTimeMillis);
        // 封装 JwtToken 对象
        JwtToken jwtToken = new JwtToken(token);
        try {
            getSubject(request,response).login(jwtToken);
            WebUtils.toHttp(response).setHeader(JwtUtils.AUTH_HEADER,token);
            return "success";
        } catch (AuthenticationException e) {
           return e.getMessage();
        }
    }
}
