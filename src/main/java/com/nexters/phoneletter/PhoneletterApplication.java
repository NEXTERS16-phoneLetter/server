package com.nexters.phoneletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class PhoneletterApplication {

  private static final String PROPERTIES =
          "spring.config.location="
                  +"classpath:/application.yml"
                  +",classpath:/dbInfo.yml";

  public static void main(String[] args) {
    new SpringApplicationBuilder(PhoneletterApplication.class)
            .properties(PROPERTIES)
            .run(args);
//    SpringApplication.run(PhoneletterApplication.class, args);
  }

}
