package com.zara.service.impl;

import com.zara.entity.User;
import com.zara.service.IUserService;
import org.springframework.stereotype.Service;

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

    @Override
    public User findUserByUsername(String userName) {
        return null;
    }
}
