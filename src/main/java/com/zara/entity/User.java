package com.zara.entity;

import com.zara.basic.EntiyBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : User
 * @description : [用户实体类]
 * @createTime : [2021/11/30 16:45]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/11/30 16:45]
 * @updateRemark : [描述说明本次修改内容]
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends EntiyBasic {
    private static final long serialVersionUID = 1L;
    //主键ID
    private Long id;

    //登录用户名
    private String username;

    //登录密码
    private String password;

    //昵称
    private String nickName;

    //账户是否被锁定 1.锁定 2.未锁定
    private int locked;
}
