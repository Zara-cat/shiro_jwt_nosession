package com.zara.entity;

import com.zara.basic.EntiyBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : Perms
 * @description : [权限实体类]
 * @createTime : [2021/12/1 11:20]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 11:20]
 * @updateRemark : [描述说明本次修改内容]
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Perms extends EntiyBasic {
    private static final long serialVersionUID = 1L;
    // id
    private Long id;
    // 权限名称
    private String name;
    //权限url
    private String url;
}
