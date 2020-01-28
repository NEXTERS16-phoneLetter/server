package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserRequestDto;

public interface UserService {

  public Long signUp(UserRequestDto userSaveRequestDto);

  public User getUser();
}
