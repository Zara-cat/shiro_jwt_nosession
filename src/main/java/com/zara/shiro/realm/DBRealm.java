package com.zara.shiro.realm;

import com.zara.entity.User;
import com.zara.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : DBRealm
 * @description : [subject 认证和授权]
 * @createTime : [2021/12/1 10:28]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 10:28]
 * @updateRemark : [描述说明本次修改内容]
 */
public class DBRealm extends AuthorizingRealm {

    @Autowired
    private IUserService service;

    /**
     * 限定这个 Realm 只处理 UsernamePasswordToken
     * @param token {@link UsernamePasswordToken}
     * @return boolean
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进入DBRealm Authentication");
        // 查询数据库，将获取到的用户安全数据封装返回
        String username = authenticationToken.getPrincipal().toString();
        //查询数据库获取用户信息。关于 service 对象 可以手动注入也可以依赖注入
        User user = service.findUserByUsername(username);
        if (!ObjectUtils.isEmpty(user)){
            if (user.getLocked() == 1){
                throw new LockedAccountException();
            }else {
                /**
                 * 将获取到的用户数据封装成 AuthenticationInfo 对象返回，此处封装为 SimpleAuthenticationInfo 对象。
                 *  参数1. 认证的实体信息，可以是从数据库中获取到的用户实体类对象或者用户名
                 *  参数2. 查询获取到的登录密码
                 *  参数3. 盐值
                 *  参数4. 当前 Realm 对象的名称，直接调用父类的 getName() 方法即可
                 */
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                        user,
                        user.getPassword(),
                        ByteSource.Util.bytes(user.getSlat()),
                        this.getName()
                );
                return info;
            }
        }else {
            throw new UnknownAccountException();
        }
    }
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入 DBRealm Authorization");
        //获取当前用户
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        //查询数据库获取用户的角色信息
        //查询数据库获取用户的权限信息
        return null;
    }


}
