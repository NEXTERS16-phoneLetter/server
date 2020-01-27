package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Override
  public User createUser(UserSaveRequestDto userSaveRequestDto) {
    User user = userRepository.save(userSaveRequestDto.toEntity());
    return user;
  }

  @Override
  public User getUser() {
    return null;
  }

  @Override
  public List<User> getAllUsers() {
    return null;
  }
}
