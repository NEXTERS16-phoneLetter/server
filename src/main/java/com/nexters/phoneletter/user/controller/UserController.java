package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.dto.UserSigninRequestDto;
import com.nexters.phoneletter.user.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/users", description = "유저 관리 API")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserServiceImpl userService;

  @ApiOperation(value = "회원가입")
  @PostMapping(value = "/signup")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Success Create User"),
      @ApiResponse(code = 400, message = "Fail Create User")
  })
  public ResponseEntity<User> signUp(@RequestBody @Valid UserSaveRequestDto userSaveRequestDto, HttpServletRequest request) {

    logger.info("signUp()");
    User user = userService.signUp(userSaveRequestDto, request);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @ApiOperation(value = "로그인")
  @PostMapping(value = "/signin")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success login"),
      @ApiResponse(code = 400, message = "Fail login")
  })
  public ResponseEntity signIn(@RequestBody @Valid UserSigninRequestDto userSigninRequestDto) {

    logger.info("signIn()");
    String token = userService.signIn(userSigninRequestDto);

    return new ResponseEntity<String>(token, HttpStatus.OK);
  }


}
