package com.nexters.phoneletter.defaultController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DefaultController {

  @Value("${spring.jwt.errorKey}")
  private String REQUEST_JWT_ATT_KEY;

  @ApiOperation(value = "테스트 API", notes = "문자열 'Hello World !' 를 리턴합니다.")
  @GetMapping("/hello")
  public String hello() {
    return "Hello World ! ";
  }

  @ApiOperation(value = "JWT 테스트 API", notes = "검증된 토큰이라면 'Good JWT !' 를 리턴합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "JWT 인증 성공"),
      @ApiResponse(code = 400, message = "JWT 인증 실패")
  })
  @GetMapping("/token")
  public String tokn() {
    return "Good JWT ! ";
  }

  @ApiOperation(value = "Error API", notes = "JWT Error 커스텀 API 로 직접 부르는 경우 없을겁니다.")
  @GetMapping("/error")
  public ResponseEntity error(HttpServletRequest request) {

    String defaultError = "Error가 발생 했습니다.";

    if (request.getAttribute(REQUEST_JWT_ATT_KEY) != null) {
      defaultError = request.getAttribute(REQUEST_JWT_ATT_KEY).toString();
    }

    return new ResponseEntity<>(defaultError, HttpStatus.BAD_REQUEST);
  }

}
