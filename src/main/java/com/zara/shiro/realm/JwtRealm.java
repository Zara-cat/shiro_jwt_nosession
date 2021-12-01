package com.zara.shiro.realm;

import com.zara.entity.Perms;
import com.zara.entity.User;
import com.zara.jwt.JwtToken;
import com.zara.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : JwtRealm
 * @description : [JwtRealm 只负责校验 JwtToken]
 * @createTime : [2021/12/1 14:11]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 14:11]
 * @updateRemark : [描述说明本次修改内容]
 */
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private IUserService service;

    /**
     * 限定这个 Realm 只处理我们自定义的 JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 此处的 SimpleAuthenticationInfo 可返回任意值，密码校验时不会用到它
     * @param authenticationToken
     * @return {@link org.apache.shiro.authc.SimpleAuthenticationInfo}
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进入 JwtRealm Authentication");
        JwtToken jwtToken = (JwtToken)authenticationToken;
        if (jwtToken.getPrincipal() == null){
            throw new AccountException("JWT token 参数异常");
        }
        //从 JWTToken 中获取当前用户
        String principal = jwtToken.getPrincipal().toString();
        //查询数据库获取用户信息
        User user = service.findUserByUsername(principal);
        if (!ObjectUtils.isEmpty(user)){
            //用户不存在
            throw new UnknownAccountException("用户不存在！");
        }
        if (user.getLocked() == 1){
            throw new LockedAccountException("该用户已被锁定，暂时无法登录");
        }
        return new SimpleAuthenticationInfo(user,principal,getName());
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入 JwtRealm Authorization");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //查询数据库获取用户的角色信息
        User dbUser = service.findRolesByUserName(user.getUsername());
        if (!ObjectUtils.isEmpty(dbUser) && !CollectionUtils.isEmpty(dbUser.getRoles())) {
            dbUser.getRoles().stream().forEach(role -> {
                info.addRole(role.getName());
                //查询数据库获取用户的权限信息
                List<Perms> perms = service.findPermsByRoleId(role.getId().intValue());
                if (!CollectionUtils.isEmpty(perms)){
                    for (Perms perm : perms) {
                        info.addStringPermission(perm.getName());
                    }
                }
            });
        }
        return info;
    }
}
