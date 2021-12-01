package com.zara.entity;

import com.zara.basic.EntiyBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : Role
 * @description : [角色实体类]
 * @createTime : [2021/12/1 11:19]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 11:19]
 * @updateRemark : [描述说明本次修改内容]
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends EntiyBasic {
    private static final long serialVersionUID = 1L;
    // id
    private Long id;
    // 角色色名称
    private String name;
    //权限集合
    private List<Perms> perms;
}
