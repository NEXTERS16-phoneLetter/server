package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;

public interface UserService {

  public User signUp(UserSaveRequestDto userSaveRequestDto);

  public User getUser();

}
