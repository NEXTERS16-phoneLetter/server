package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.repository.UserRepository;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
@Api(value = "/user",description = "유저 정보 CRUD API")
public class UserController {

  UserRepository userRepository;

  @ApiOperation(value = "유저 회원 가입")
  @PostMapping("")
  @ApiResponses(value={
          @ApiResponse(code=201,message = "Success Create User"),
          @ApiResponse(code=400,message = "Fail Create User")
  })
  public String  saveUser(@RequestBody UserSaveRequestDto userSaveRequestDto){
    userRepository.save(userSaveRequestDto.toEntity());
    return "success!";
  }


}