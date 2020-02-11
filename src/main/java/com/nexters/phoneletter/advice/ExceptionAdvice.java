package com.nexters.phoneletter.advice;

import com.nexters.phoneletter.advice.exception.CustomPasswordNotMatchException;
import com.nexters.phoneletter.advice.exception.CustomUserNotFoundException;
import com.nexters.phoneletter.response.CommonResult;
import com.nexters.phoneletter.response.ResponseService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

  private final ResponseService responseService;

  private static final String DEFAULT_ERR_MSG = "기본 에러 발생";
  private static final String USER_NOT_FOUND_ERR_MSG = "존재하지 않는 회원 아이디 입니다.";
  private static final String PASSWORD_NOT_MATCH_ERR_MSG = "패스워드가 일치하지 않습니다.";

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult defaultException(HttpServletRequest request, Exception e) {
    return responseService.getFailResult(404, DEFAULT_ERR_MSG);
  }

  @ExceptionHandler(CustomUserNotFoundException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected CommonResult userNotFoundException(HttpServletRequest request, Exception e) {
    return responseService.getFailResult(401, USER_NOT_FOUND_ERR_MSG);
  }

  @ExceptionHandler(CustomPasswordNotMatchException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected CommonResult passwordNotMatchException(HttpServletRequest request, Exception e) {
    return responseService.getFailResult(401, PASSWORD_NOT_MATCH_ERR_MSG);
  }

}
