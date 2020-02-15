package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.KakaoUserRequestDto;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.dto.UserSigninRequestDto;
import com.nexters.phoneletter.user.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"회원 REST API"})
@SwaggerDefinition(tags = {
    @Tag(name = "회원 REST API", description = "회원 관리 REST API")
})
@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

  private final UserServiceImpl userService;

  @ApiOperation(value = "일반 유저 회원가입")
  @PostMapping(value = "/signup")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "회원 가입 성공"),
      @ApiResponse(code = 400, message = "회원 가입 실패")
  })
  public ResponseEntity<User> signUp(@RequestBody @Valid UserSaveRequestDto userSaveRequestDto) {

    log.info("signUp()");
    User user = userService.signUp(userSaveRequestDto);

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @ApiOperation(value = "일반 유저 로그인")
  @PostMapping(value = "/signin")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "로그인 성공, JWT 반환"),
      @ApiResponse(code = 400, message = "회원가입 실패")
  })
  public ResponseEntity signIn(@RequestBody @Valid UserSigninRequestDto userSigninRequestDto) {

    log.info("signIn()");
    String token = userService.signIn(userSigninRequestDto);

    return new ResponseEntity<>(token, HttpStatus.OK);
  }

  @ApiOperation(value = "카카오 로그인")
  @PostMapping(value = "/kakaoSignin")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "카카오 유저 로그인 성공, JWT 반환"),
      @ApiResponse(code = 400, message = "카카오 유저 로그인 실패")
  })
  public ResponseEntity kakaoSignin(@RequestBody @Valid KakaoUserRequestDto kakaoUserRequestDto
      ) {

    log.info("kakaoSignin()");
    String token = userService.kakaoLogin(kakaoUserRequestDto);

    return new ResponseEntity<>(token, HttpStatus.OK);
  }

  @ApiOperation(value = "카카오 회원가입")
  @PostMapping(value = "/kakaoSignup")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "카카오 유저 회원가입 성공, JWT 반환"),
      @ApiResponse(code = 400, message = "카카오 유저 회원가입 실패")
  })
  public ResponseEntity kakaoRegister(@RequestBody @Valid KakaoUserRequestDto kakaoUserRequestDto) {

    log.info("kakaoRegister()");
    String token = userService.kakaoRegister(kakaoUserRequestDto);

    return new ResponseEntity<>(token, HttpStatus.CREATED);
  }

}
