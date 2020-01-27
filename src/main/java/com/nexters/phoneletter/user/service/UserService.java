package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;

import java.util.List;

public interface UserService {

  public User createUser(UserSaveRequestDto userSaveRequestDto);

  public User getUser();

  public List<User> getAllUsers();

}
