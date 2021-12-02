package com.zara.service.impl;

import com.zara.dao.IUserDao;
import com.zara.entity.Perms;
import com.zara.entity.User;
import com.zara.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : UserServiceImpl
 * @description : [用户操作业务实现类]
 * @createTime : [2021/12/1 10:36]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 10:36]
 * @updateRemark : [描述说明本次修改内容]
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao dao;

    @Override
    public User findUserByUsername(String userName) {
        return dao.findByUserName(userName);
    }

    @Override
    public User findRolesByUserName(String username) {
        return dao.findRolesByUserName(username);
    }

    @Override
    public List<Perms> findPermsByRoleId(int roleId) {
        return dao.findPermsByRoleId(roleId);
    }
}
