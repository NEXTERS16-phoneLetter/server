package com.nexters.phoneletter.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    /** getConnection()을 호출할 때마다 새로운 LettuceConnection을 생성합니다. */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /** redisTemplate을 사용해서 redis 데이터를 직접 컨트롤할 수 있습니다. */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        /**
         * key는 string으로 value는 json 형태로 저장하기 위해
         * 아래 코드는 객체를 json 형태로 깨지지 않고 받기 위한 직렬화 작업입니다.
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // (String) key Serializer 설정
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // (Json) value Serializer 설정
        return redisTemplate;
    }

}
