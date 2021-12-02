package com.zara.shiro.credentialsmatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zara.jwt.JwtUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : JwtCredentialsMatcher
 * @description : [自定义 jwtRealm 凭证校验器]
 * @createTime : [2021/12/1 15:10]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 15:10]
 * @updateRemark : [描述说明本次修改内容]
 */
public class JwtCredentialsMatcher implements CredentialsMatcher {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * JwtCredentialsMatcher只需验证JwtToken内容是否合法
     */

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = authenticationToken.getCredentials().toString();
        String username = authenticationToken.getPrincipal().toString();
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtUtils.SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            logger.error(e.getMessage());
        }
        return false;
    }
}