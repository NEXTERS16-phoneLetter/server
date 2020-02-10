package com.nexters.phoneletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableCaching // 캐시 사용
@EnableRedisHttpSession
@SpringBootApplication
public class PhoneletterApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhoneletterApplication.class, args);
  }

}
