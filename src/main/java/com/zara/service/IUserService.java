package com.zara.service;

import com.zara.entity.Perms;
import com.zara.entity.User;


import java.util.List;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : IUserService
 * @description : [用户操作的业务接口]
 * @createTime : [2021/12/1 10:34]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 10:34]
 * @updateRemark : [描述说明本次修改内容]
 */
public interface IUserService {
    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return {@link User}
     */
    User findUserByUsername(String userName);
    /**
     * 根据用户名查询角色集合
     * @param username 用户名
     * @return {@link User}
     */
    User findRolesByUserName(String username);

    /**
     * 根据角色id查询权限集合
     * @param roleId 根据角色id
     * @return {@link Perms}
     */
    List<Perms> findPermsByRoleId(int roleId);
}
