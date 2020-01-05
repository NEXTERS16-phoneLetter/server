package com.nexters.phoneletter.testController;

import com.nexters.phoneletter.domain.user.UserRepository;
import com.nexters.phoneletter.domain.user.UserSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TestController {

  UserRepository userRepository;

  @GetMapping("/hello")
  public String hello() {
    return "Hello World ! ";
  }

  @PostMapping("/user")
  public void saveUser(@RequestBody UserSaveRequestDto userSaveRequestDto){
    userRepository.save(userSaveRequestDto.toEntity());
  }
}
