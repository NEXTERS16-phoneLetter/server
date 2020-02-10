package com.nexters.phoneletter.advice;

import com.nexters.phoneletter.advice.exception.CustomJWTExpireException;
import com.nexters.phoneletter.advice.exception.CustomJWTValidException;
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

  private static final String USER_NOT_FOUND_ERR_MSG = "로그인에 실패 하였습니다.";
  private static final String JWT_EXPIRE_ERR_MSG = "토큰 기간이 만료되었습니다.";
  private static final String JWT_NOT_VALID_ERR_MSG = "유효한 토큰이 아닙니다.";

  @ExceptionHandler(CustomUserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected CommonResult userNotFoundException(HttpServletRequest request, Exception e) {
    return responseService.getFailResult(400, USER_NOT_FOUND_ERR_MSG);
  }

  @ExceptionHandler(CustomPasswordNotMatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected CommonResult passwordNotMatchException(HttpServletRequest request, Exception e) {
    return responseService.getFailResult(400, USER_NOT_FOUND_ERR_MSG);
  }

  @ExceptionHandler(CustomJWTExpireException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected CommonResult jwtExpireException(HttpServletRequest request, Exception e) {
    return responseService.getFailResult(401, JWT_EXPIRE_ERR_MSG);
  }

  @ExceptionHandler(CustomJWTValidException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected CommonResult jwtNotValidException(HttpServletRequest request, Exception e) {
    return responseService.getFailResult(401, JWT_NOT_VALID_ERR_MSG);
  }
}
