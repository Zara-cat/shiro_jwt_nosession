package com.zara.controller;

import com.zara.utils.response.Responder;
import com.zara.utils.response.ResultDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : ArticleController
 * @description : [检验注解  ArticleController有两个示例接口：访问 /article/delete 需要 admin 角色；访问 /article/read 需要 article:read 权限。]
 * @createTime : [2021/12/2 10:23]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/2 10:23]
 * @updateRemark : [描述说明本次修改内容]
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/delete")
    @RequiresRoles(value = {"admin"})
    public ResultDTO<Object> deleteArticle(ModelMap model) {
        System.out.println("访问 delete");
        return Responder.successful("文章删除成功", null);
    }

    @GetMapping("/read")
    @RequiresPermissions(value = {"article:read"})
    public ResultDTO<Object> readArticle(ModelMap model) {
        System.out.println("访问 read");
        return Responder.successful("请您鉴赏！", null);
    }
}