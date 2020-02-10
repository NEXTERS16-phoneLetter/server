package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.dto.UserAuthDto;
import com.nexters.phoneletter.user.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserAuthController {

    @Autowired
    UserAuthService userAuthService;

    // 인증 코드 문자 발송
    @PostMapping("/sms")
    public HttpStatus sendSms(UserAuthDto userAuthDto){
        userAuthService.sendSms(userAuthDto.getPhoneNumber());
        return HttpStatus.OK;
    }

    // 전화번호 인증 확인
    @PostMapping("/sms/verify")
    public HttpStatus verifyCode(HttpServletRequest request, UserAuthDto userAuthDto){
        if(userAuthService.isMatchVerifyCode(userAuthDto, request)) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

}
