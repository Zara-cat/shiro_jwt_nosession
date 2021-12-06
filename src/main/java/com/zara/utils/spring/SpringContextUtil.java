package com.zara.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


/**
 * Title: Spring 上下文工具
 * Description:该工具类必须声明为Spring 容器里的一个Bean对象，否则无法自动注入ApplicationContext对象
 * Date: 2021/4/16 10:09
 *
 * @author JiaQi Ding
 * @version 1.0
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * 根据Bean名称获取Bean对象
     *
     * @param name Bean名称
     * @return 对应名称的Bean对象
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 根据Bean的类型获取对应的Bean
     *
     * @param requiredType Bean类型
     * @return 对应类型的Bean对象
     */
    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    /**
     * 根据Bean名称获取指定类型的Bean对象
     *
     * @param name         Bean名称
     * @param requiredType Bean类型（可为空）
     * @return 获取对应Bean名称的指定类型Bean对象
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    /**
     * 判断是否包含对应名称的Bean对象
     *
     * @param name Bean名称
     * @return 包含：返回true，否则返回false。
     */
    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    /**
     * 获取对应Bean名称的类型
     *
     * @param name Bean名称
     * @return 返回对应的Bean类型
     */
    public static Class<?> getType(String name) {
        return context.getType(name);
    }

    /**
     * 获取上下文对象，可进行各种Spring的上下文操作
     *
     * @return Spring上下文对象
     */
    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * Spring在bean初始化后会判断是不是ApplicationContextAware的子类
     * 如果该类是,setApplicationContext()方法,会将容器中ApplicationContext作为参数传入进去
     */
    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
