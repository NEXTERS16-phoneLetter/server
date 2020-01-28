package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.dto.UserRequestDto;
import com.nexters.phoneletter.user.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
@Api(value = "/users", description = "유저 정보 CRUD API")
public class UserController {

  private UserServiceImpl userService;

  @ApiOperation(value = "유저 회원 가입")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Success Create User"),
      @ApiResponse(code = 400, message = "Fail Create User")
  })
  public ResponseEntity signUp(@RequestBody @Valid UserRequestDto userSaveRequestDto) {
    userService.signUp(userSaveRequestDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }


}
