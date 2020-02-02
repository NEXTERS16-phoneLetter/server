package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserRequestDto;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public User signUp(UserRequestDto userSaveRequestDto) {
    return userRepository.save(userSaveRequestDto.toEntity(passwordEncoder));
  }

  @Override
  public User getUser() {
    return null;
  }

}
