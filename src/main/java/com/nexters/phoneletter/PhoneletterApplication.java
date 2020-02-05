package com.nexters.phoneletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching // 캐시 사용
@SpringBootApplication
public class PhoneletterApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhoneletterApplication.class, args);
  }

}
