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
@Api(value = "/users", description = "유저 관리 API")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

  private final UserServiceImpl userService;

  @ApiOperation(value = "회원가입")
  @PostMapping(value = "/signup")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Success Create User"),
      @ApiResponse(code = 400, message = "Fail Create User")
  })
  public ResponseEntity<User> signUp(@RequestBody @Valid UserSaveRequestDto userSaveRequestDto) {

    log.info("signUp()");
    User user = userService.signUp(userSaveRequestDto);

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @ApiOperation(value = "로그인")
  @PostMapping(value = "/signin")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success login"),
      @ApiResponse(code = 400, message = "Fail login")
  })
  public ResponseEntity signIn(@RequestBody @Valid UserSigninRequestDto userSigninRequestDto) {

    log.info("signIn()");
    String token = userService.signIn(userSigninRequestDto);

    return new ResponseEntity<>(token, HttpStatus.OK);
  }

  @ApiOperation(value = "카카오 로그인")
  @PostMapping(value = "/kakaoSignin")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Success Create Kakao User"),
      @ApiResponse(code = 400, message = "Fail login")
  })
  public ResponseEntity kakaoSignin(@RequestBody @Valid KakaoUserRequestDto kakaoUserRequestDto,
      HttpSession httpSession) {

    log.info("kakaoSignin()");
    String token = userService.kakaoLogin(kakaoUserRequestDto);

    return new ResponseEntity<>(token, HttpStatus.CREATED);
  }

  @ApiOperation(value = "카카오 회원가입")
  @PostMapping(value = "/kakaoSignup")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success signup"),
      @ApiResponse(code = 400, message = "Fail signup")
  })
  public ResponseEntity kakaoRegister(@RequestBody @Valid KakaoUserRequestDto kakaoUserRequestDto,
      HttpSession httpSession) {

    log.info("kakaoRegister()");
    String token = userService.kakaoRegister(kakaoUserRequestDto);

    return new ResponseEntity<>(token, HttpStatus.OK);
  }

}
