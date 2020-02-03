package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.dto.UserAuthDto;
import com.nexters.phoneletter.user.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// sms 전송 테스트를 위한 컨트롤러
@RestController
public class UserAuthController {

    @Autowired
    UserAuthService userAuthService;

    // 인증 코드 문자 발송
    @PostMapping("/sendSms")
    public ResponseEntity sendSms(String phoneNumber){
        if(userAuthService.sendSms(phoneNumber)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 전화번호 인증 확인
    @PostMapping("/phoneNumberCert")
    public ResponseEntity phoneNumberCert(UserAuthDto userAuthDto){
        if(userAuthService.phoneNumberCert(userAuthDto)){
            // 나중에 session을 통해 인증 여부 redis에 저장
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
