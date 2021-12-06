shiro + mybatis + jwt +redis 实现前后端无状态token认证授权（内含token刷新机制）
***
# 简介
1. 通过对shiro的学习，我发现shiro在处理前后端未分离的项目那是一个真的方便，如：thymeleaf、jsp 的 集成整合，但是目前的行业需求已经方案大多都是前后端业务。也就是在前后端业务处理的时候，没有了session 域。接下来再进行 shiro 进行认证授权处理会很麻烦。

2. 在网上找到了很多对于前后端分离使用 shiro 的教程文档，我找到两套解决方案，<font face="微软雅黑"  color=#FF0000 >其一是禁 shiro 中的 sessionManager 通过JWT这种方式来处理，其二就是 自定义一下 shiro 的session处理方式</font>。

3. 通过网上找到的教程，把 springboot + shiro + jwt + redis 的小 demo 进行整理。如果给到了你们小小的帮助，给一个小小的 star 吧

   

# 案例实现逻辑

1. 我们在 shiro 的基础上自定义登录校验，继续整合JWT，使其称为支持服务端无状态登录，即 token 登录。实现方式: 就是 自定义filter 和实现 realm方式 使用 shiro 中 subject 的login() 方法进行 token 校验。
2. 由上我们仅仅实现了 token 登录，但是不能实现 token 的可控性，和 token 的自动刷新。
3. 这样会导致 2 种问题。第一：如果 token 在 5 分钟后过期，用户 5 分钟后还需要使用系统，那么可能就需要重新登录。这个不是 token 设置时间长短的问题。第二： token 的有效期内，即使用户退出了登录，token 依然有效的，依然可以使用，这是非常不安全的。所以实现 token 的可控性是非常重要的。


# 可控Token以及Token刷新

## Shiro + JWT 实现无状态鉴权机制
1. 首先使用 post 请求传递用户名、密码进行login登入，如果经过 shiro 的 realm 认证以后，成功后在响应请求头上带上 生成的 token，以后每次请求接口请求头都带上这个 token。失败就直接返回错误信息就可以了。
2. 关于 token 的校验，自定义一 shiro 的一个拦截器，然后实现所有请求拦截，当然你也可以指定拦截 url ，然后在 自定义一个  Token 对象(这里说的 Token 对象就跟我们登录时候UsernamePasswordToken一样)，然后经过我们自定义的 JWTRealm 进行Token 有效校验，看看 前端传递过来的 Token 是否是过期的、无效的、过期需要刷新的、还是等等无效字符串无法解析的。最后做到 Token 的可控以及 Token 的刷新。
##  AccessToken 、RefreshToken 概念
1. AccessToken ：用于请求传输过程中的用户授权标识，客户端每次请求都需要携带，处于安全考虑通常有效时长较短。
2. 

