package com.mcloud.storageweb.config;


import com.mcloud.storageweb.util.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 14:40 2018/6/5
 * @Modify By:
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    /** * 注入 RedisConnectionFactory */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;



    /**
     *  实例化 RedisTemplate 对象
     *  Primary 在autoware时优先选用我注册的bean.
     *  因为在redis框架中有注册一个StringRedisTemplate,避免注入冲突
     *  @return
     */
    @Bean
    @Primary
    public RedisTemplate<String, Object> functionDomainRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }
    /** * 设置数据存入 redis 的序列化方式 *
     * * @param redisTemplate * @param factory
     * */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer()); /*  */
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }
}

