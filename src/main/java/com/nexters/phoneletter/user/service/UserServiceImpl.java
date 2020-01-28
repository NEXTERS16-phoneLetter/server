package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Override
  public User signUp(UserSaveRequestDto userSaveRequestDto) {
    User user = userRepository.save(userSaveRequestDto.toEntity());
    return user;
  }

  @Override
  public User getUser() {
    return null;
  }

}
