package com.zara.shiro.config;

import com.zara.jwt.JwtTokenFilter;
import com.zara.shiro.MultiRealmAuthenticator;
import com.zara.shiro.credentialsmatcher.JwtCredentialsMatcher;
import com.zara.shiro.realm.DBRealm;
import com.zara.shiro.realm.JwtRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : ShiroConfig
 * @description : [描述说明该类的功能]
 * @createTime : [2021/12/1 15:52]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/1 15:52]
 * @updateRemark : [描述说明本次修改内容]
 */
@Configuration
public class ShiroConfig {
    /**
     * 交由 Spring 来自动地管理 Shiro-Bean 的生命周期
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启 shiro aop 注解支持，使用代理方式，所以需要开启代码支持
     * @param securityManager 安全管理器
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }

    /**
     * 配置 ModularRealmAuthenticator
     * @return
     */
    @Bean
    public ModularRealmAuthenticator authenticator(){
        ModularRealmAuthenticator authenticator = new MultiRealmAuthenticator();
        // 设置多 Realm 的认证策略，默认 AtLeastOneSuccessfulStrategy
        AuthenticationStrategy strategy = new FirstSuccessfulStrategy();
        authenticator.setAuthenticationStrategy(strategy);
        return authenticator;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证
     * @return
     */
    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }


    /**
     * 不向 Spring容器中注册 JwtFilter Bean，防止 Spring 将 JwtFilter 注册为全局过滤器
     * 全局过滤器会对所有请求进行拦截，而本例中只需要拦截除 /login 和 /logout 外的请求
     * 另一种简单做法是：直接去掉 jwtFilter()上的 @Bean 注解
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean<Filter> registration(JwtTokenFilter filter){
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>(filter);
        registration.setEnabled(false);
        return registration;
    }


    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    /**
     * JwtRealm 配置，并设置好JWT token 的 自定义凭证器
     * @return
     */
    @Bean
    public JwtRealm jwtRealm(){
        JwtRealm jwtRealm = new JwtRealm();
        //设置自定义的 CredentialsMatcher
        CredentialsMatcher credentialsMatcher = new JwtCredentialsMatcher();
        jwtRealm.setCredentialsMatcher(credentialsMatcher);
        return jwtRealm;
    }

    /**
     * DbRealm 配置，并设置密码凭证器 md5 + hash散列
     * @return
     */
    @Bean
    public DBRealm dbRealm(){
        DBRealm dbRealm = new DBRealm();
        //设置加密算法密码凭证器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1024);
        dbRealm.setCredentialsMatcher(credentialsMatcher);
        return dbRealm;
    }

    /**
     * 配置 securityManager
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //1. Authenticator
        securityManager.setAuthenticator(authenticator());

        //2.Realm
        List<Realm> realms = new ArrayList<>(16);
        realms.add(jwtRealm());
        realms.add(dbRealm());
        securityManager.setRealms(realms);

        //3.关闭 shiro 自带的 session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 配置访问资源需要的权限
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/authorized");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        //添加 jwt 专用过滤器 ，拦截 /login 和 /login 外的请求
        Map<String,Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwtTokenFilter",jwtTokenFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/login","anon"); // 可匿名访问
        filterChainDefinitionMap.put("/logout","logout");// 推出登录
        filterChainDefinitionMap.put("/**","jwtTokenFilter,authc");//需要登录才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }



}
