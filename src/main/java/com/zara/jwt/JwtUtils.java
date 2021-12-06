package com.zara.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zara.constant.SysConstant;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : JwtUtils
 * @description : [JWT 签发工具类]
 * @createTime : [2021/11/29 11:57]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/11/29 11:57]
 * @updateRemark : [描述说明本次修改内容]
 */
public class JwtUtils {

    // 过期时间
    private static final long EXPIRE_TIME = SysConstant.ACCESSToken_EXPIRE_TIME;

    // 私钥
    public static final String SECRET = SysConstant.ACCESSToken_SECRET;

    // 请求头
    public static final String AUTH_HEADER = SysConstant.ACCESS_AUTH_HEADER;


    /**
     * 生成签名
     */
    public static String sign(String username, String secret,Long current) {
        try {
            Date date = new Date(current + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username，nickname信息
            return JWT.create()
                    .withClaim("username", username) //私有声明
                    .withClaim("current",current) //当前时间截点
                    .withExpiresAt(date) //过期时间
                    .withIssuedAt(new Date()) //签发时间
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            return null;
        }
    }

    /**
     * 判断token是否过期
     * @param token token
     * @return 牌无效时返回 false
     */
    public static boolean overdue(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 获取过期时间
            Date deadline = jwt.getExpiresAt();
            if (deadline.before(new Date())) {
                return true;
            }
        } catch (JWTDecodeException exception) {
            //Invalid token
            // 令牌解码失败
        }
        return false;
    }

    /**
     * 验证token是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                                      .withClaim("username", username)
                                      .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    /**
     * 获得token中的自定义信息，无需secret解密也能获得
     */
    public static String getClaimFiled(String token, String filed) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(filed).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 获取 token的签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token中的时间搓
     * @param token
     * @return
     */
    public static Long getExpire(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("current").asLong();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }





    /**
     * 刷新 token的过期时间
     */
    public static String refreshTokenExpired(String token, String secret) {
        DecodedJWT jwt = JWT.decode(token);
        Map<String, Claim> claims = jwt.getClaims();
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTCreator.Builder builer = JWT.create().withExpiresAt(date);
            for (Map.Entry<String, Claim> entry : claims.entrySet()) {
                builer.withClaim(entry.getKey(), entry.getValue().asString());
            }
            return builer.sign(algorithm);
        } catch (JWTCreationException e) {
            return null;
        }
    }

    /**
     * 生成16位随机盐
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(16).toHex();
        return hex;
    }
}
