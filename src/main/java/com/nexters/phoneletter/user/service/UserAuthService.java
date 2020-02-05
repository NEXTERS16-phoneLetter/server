package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.dto.UserAuthDto;

public interface UserAuthService {
    public void sendSmsRestTemplate(String phoneNumber);
    public boolean isMatchVerifyCode(UserAuthDto userAuthDto);
}
