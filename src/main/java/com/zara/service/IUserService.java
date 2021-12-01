package com.zara.service;

import com.zara.entity.User;

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
    User findUserByUsername(String userName);
}
