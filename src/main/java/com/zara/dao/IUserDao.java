package com.zara.dao;

import com.zara.entity.Perms;
import com.zara.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : IUserDao
 * @description : [用户操作业务数据层]
 * @createTime : [2021/12/1 13:13]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 13:13]
 * @updateRemark : [描述说明本次修改内容]
 */
@Mapper
public interface IUserDao {
    /**
     * 添加用户
     * @param user {@link User}
     */
    void save(@Param("user") User user);

    /**
     * 根据用户命查询用户
     * @param username 用户名
     * @return {@link User}
     */
    User findByUserName(@Param("username") String username);

    /**
     * 根据用户名查询角色集合
     * @param username 用户名
     * @return {@link User}
     */
    User findRolesByUserName(@Param("username") String username);

    /**
     * 根据角色id查询权限集合
     * @param roleId 根据角色id
     * @return {@link Perms}
     */
    List<Perms> findPermsByRoleId(@Param("roleId") int roleId);
}
