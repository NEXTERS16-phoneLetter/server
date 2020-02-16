package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.dto.UserAuthDto;

import javax.servlet.http.HttpServletRequest;

public interface UserAuthService {
    // 인증 코드 문자 발송
    public void sendSms(UserAuthDto userAuthDto);

    // 사용자가 입력한 인증 코드 확인
    public boolean isMatchVerifyCode(UserAuthDto userAuthDto, HttpServletRequest request);

    // 전화번호 인증 여부 확인
    public boolean isVerifiedPhoneNumber(String phoneNumber, HttpServletRequest request);
}