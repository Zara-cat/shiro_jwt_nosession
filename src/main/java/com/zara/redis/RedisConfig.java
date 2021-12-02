package com.zara.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author : [Zara-cat]
 * @version : [v1.0]
 * @className : RedisConfig
 * @description : [redis 配置文件]
 * @createTime : [2021/12/2 11:45]
 * @updateUser : [Zara-cat]
 * @updateTime : [2021/12/2 11:45]
 * @updateRemark : [描述说明本次修改内容]
 */
//为什么需要配置redis，因为redis的默认序列化是jdk序列化，这样的序列化是以转义字符的形式存储的，不方便查看，我们需要对其进行修改，以方便查看
@Configuration
public class RedisConfig {
    //编写自己的redisTemplate
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        //为了自己开发使用方便，一般使用 <String,Object> 类型
        RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(redisConnectionFactory);

        //序列化配置
        //json 序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        //String 序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //key 使用 String 的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash 的 key 也是用 String
        template.setHashKeySerializer(stringRedisSerializer);
        //value 使用 json 序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash 的 value 使用 json 序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
