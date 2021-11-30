shiro + mybatis + jwt 实现前后端无状态token认证授权（内含token刷新机制）
***
# 简介
1. 通过对shiro的学习，我发现shiro在处理前后端未分离的项目那是一个真的方便，如：thymeleaf、jsp 的 集成整合，但是目前的行业需求已经方案大多都是前后端业务。也就是在前后端业务处理的时候，没有了session 域。接下来再进行 shiro 进行认证授权处理会很麻烦。
2. 在网上找到了很多对于前后端分离使用 shiro 的教程文档，我找到两套解决方案，<font face="微软雅黑"  color=#FF0000 >其一是禁 shiro 中的 sessionManager 通过JWT这种方式来处理，其二就是 自定义一下 shiro 的session处理方式</font>。
3. 通过网上找到的教程，把 springboot + shiro + jwt 的小 demo 进行整理。如果给到了你们小小的帮助，给一个小小的 start 吧