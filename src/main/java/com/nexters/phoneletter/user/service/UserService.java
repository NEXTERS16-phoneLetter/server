package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.dto.UserSigninRequestDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

  public User signUp(UserSaveRequestDto userSaveRequestDto, HttpServletRequest request);

  public String signIn(UserSigninRequestDto userSigninRequestDto);

  public User getUser();
}
