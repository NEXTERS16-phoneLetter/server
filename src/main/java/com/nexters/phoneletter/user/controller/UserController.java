package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.repository.UserRepository;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

  UserRepository userRepository;

  @ApiOperation(value = "유저 회원 가입")
  @PostMapping("/user")
  public String  saveUser(@RequestBody UserSaveRequestDto userSaveRequestDto){
    userRepository.save(userSaveRequestDto.toEntity());
    return "success!";
  }


}
