package com.zara.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : JwtToken
 * @description : [Jwt Shiro token]
 * @createTime : [2021/12/1 14:09]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 14:09]
 * @updateRemark : [描述说明本次修改内容]
 */
public class JwtToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;

    // 加密后的 JWT token串
    private String token;

    private String userName;

    public JwtToken(String token) {
        this.token = token;
        //获得token中的自定义信息
        this.userName = JwtUtils.getClaimFiled(token, "username");
    }

    @Override
    public Object getPrincipal() {
        return this.userName;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
