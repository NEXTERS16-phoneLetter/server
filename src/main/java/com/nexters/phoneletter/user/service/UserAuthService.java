package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.dto.UserAuthDto;

import javax.servlet.http.HttpServletRequest;

public interface UserAuthService {
    public void sendSms(String phoneNumber);
    public boolean isMatchVerifyCode(UserAuthDto userAuthDto, HttpServletRequest request);
}