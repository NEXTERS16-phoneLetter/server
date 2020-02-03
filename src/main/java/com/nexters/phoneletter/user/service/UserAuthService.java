package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.dto.UserAuthDto;

public interface UserAuthService {

    public boolean sendSms(String phoneNumber); // 인증 코드 문자 발송
    public boolean phoneNumberCert(UserAuthDto userAuthDto); // 전화번호 인증

}
