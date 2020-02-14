package com.nexters.phoneletter.testController;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

  @Value("${spring.jwt.errorKey}")
  private String REQUEST_JWT_ATT_KEY;

  @GetMapping("/hello")
  public String hello() {
    return "Hello World !";
  }

  @GetMapping("/token")
  public String tokn() {
    return "Good JWT ! ";
  }

  @GetMapping("/error")
  public ResponseEntity error(HttpServletRequest request) {

    String defaultError = "Error가 발생 했습니다.";

    if (request.getAttribute(REQUEST_JWT_ATT_KEY) != null) {
      defaultError = request.getAttribute(REQUEST_JWT_ATT_KEY).toString();
    }

    return new ResponseEntity<>(defaultError, HttpStatus.BAD_REQUEST);
  }

}
