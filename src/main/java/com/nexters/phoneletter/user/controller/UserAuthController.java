package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.dto.UserAuthDto;
import com.nexters.phoneletter.user.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "전화번호 인증 API")
@RestController
public class UserAuthController {

    @Autowired
    UserAuthService userAuthService;

    // 인증 코드 문자 발송
    @ApiOperation(value = "전화번호 인증", notes = "문자로 인증번호 6자리를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Fail")
    })
    @PostMapping("/sms")
    public HttpStatus sendSms(@RequestBody String phonNumber){
        userAuthService.sendSms(phonNumber);
        return HttpStatus.OK;
    }

    // 전화번호 인증 확인
    @ApiOperation(value = "인증번호 확인", notes = "사용자가 입력한 인증번호 6자리가 일치하는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Fail")
    })
    @PostMapping("/sms/verify")
    public HttpStatus verifyCode(HttpServletRequest request, @RequestBody UserAuthDto userAuthDto){
        if(userAuthService.isMatchVerifyCode(userAuthDto, request)) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

}
