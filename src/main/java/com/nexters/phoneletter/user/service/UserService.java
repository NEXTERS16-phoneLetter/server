package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.dto.UserSigninRequestDto;

public interface UserService {

  public User signUp(UserSaveRequestDto userSaveRequestDto);

  public String signIn(UserSigninRequestDto userSigninRequestDto);

  public User getUser();
}
