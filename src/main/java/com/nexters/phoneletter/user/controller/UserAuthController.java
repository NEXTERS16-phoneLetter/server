package com.nexters.phoneletter.user.controller;

import com.nexters.phoneletter.user.dto.UserAuthDto;
import com.nexters.phoneletter.user.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = {"전화번호 인증 REST API"})
@SwaggerDefinition(tags = {
    @Tag(name = "전화번호 인증 REST API", description = "전화번호 인증 관리 REST API")
})
@RestController
public class UserAuthController {

    @Autowired
    UserAuthService userAuthService;

    // 인증 코드 문자 발송
    @ApiOperation(value = "전화번호 인증", notes = "문자로 인증번호 6자리를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "전화 번호 인증 시작 하기 성공"),
            @ApiResponse(code = 400, message = "전화 번호로 인증 시작 하기 실패")
    })
    @PostMapping("/sms")
    public HttpStatus sendSms(@RequestBody UserAuthDto userAuthDto){
        log.info("sendSms()");
        userAuthService.sendSms(userAuthDto);
        return HttpStatus.OK;
    }

    // 전화번호 인증 확인
    @ApiOperation(value = "인증번호 확인", notes = "사용자가 입력한 인증번호 6자리가 일치하는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증 번호로 인증 성공"),
            @ApiResponse(code = 400, message = "인증 번호로 인증 실패")
    })
    @PostMapping("/sms/verify")
    public HttpStatus verifyCode(HttpServletRequest request, @RequestBody UserAuthDto userAuthDto) {
        log.info("verifyCode()");
        if (userAuthService.isMatchVerifyCode(userAuthDto, request)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

}
