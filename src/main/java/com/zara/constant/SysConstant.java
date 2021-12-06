package com.zara.constant;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : SysConstant
 * @description : [系统常量]
 * @createTime : [2021/12/4 23:25]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/4 23:25]
 * @updateRemark : [描述说明本次修改内容]
 */
public class SysConstant {
    //redis refreshToken prefix
    public final static String REFRESHTOKEN_PRE = "RefreshToken_";
    //redis refreshToken valid time unit 60 minute
    public final static int REFRESHTOKEN_TIME = 60*60;
    //client accessToken valid time 5 minute
    public final static long ACCESSToken_EXPIRE_TIME = 5 * 60 * 1000;
    //accessToken private key
    public final static String ACCESSToken_SECRET = "SECRET_VALUE";
    //client request header need with
    public final static String ACCESS_AUTH_HEADER="X-Authorization-With";

}
