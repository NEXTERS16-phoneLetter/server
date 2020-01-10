package com.nexters.phoneletter.testController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TestController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello World ! ";
  }

}
