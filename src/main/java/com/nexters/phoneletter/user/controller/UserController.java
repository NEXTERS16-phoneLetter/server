package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.repository.UserRepository;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

  UserRepository userRepository;

  @PostMapping("/user")
  public void saveUser(@RequestBody UserSaveRequestDto userSaveRequestDto){
    userRepository.save(userSaveRequestDto.toEntity());
  }
}
