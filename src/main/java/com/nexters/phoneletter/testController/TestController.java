package com.nexters.phoneletter.testController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello World ! ";
  }

  @GetMapping("/token")
  public String tokn() {
    return "Good JWT ! ";
  }

}
