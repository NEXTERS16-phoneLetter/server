package com.nexters.phoneletter.advice;

import com.nexters.phoneletter.advice.exception.CustomJWTExpireException;
import com.nexters.phoneletter.advice.exception.CustomJWTValidException;
import com.nexters.phoneletter.advice.exception.CustomPasswordNotMatchException;
import com.nexters.phoneletter.advice.exception.CustomSignUpFailException;
import com.nexters.phoneletter.advice.exception.CustomUserNotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

  private static final String USER_NOT_FOUND_ERR_MSG = "로그인에 실패 하였습니다.";
  private static final String USER_SIGNUP_FAIL_ERR_MSG = "회원가입에 실패 하였습니다.";
  private static final String JWT_EXPIRE_ERR_MSG = "토큰 기간이 만료되었습니다.";
  private static final String JWT_NOT_VALID_ERR_MSG = "유효한 토큰이 아닙니다.";

  @ExceptionHandler(CustomUserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity userNotFoundException(HttpServletRequest request, Exception e) {
    return new ResponseEntity<>(USER_NOT_FOUND_ERR_MSG,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomPasswordNotMatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity passwordNotMatchException(HttpServletRequest request, Exception e) {
    return new ResponseEntity<>(USER_NOT_FOUND_ERR_MSG,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomSignUpFailException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity userSignInFailException(HttpServletRequest request, Exception e) {
    return new ResponseEntity<>(USER_SIGNUP_FAIL_ERR_MSG,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomJWTExpireException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected ResponseEntity jwtExpireException(HttpServletRequest request, Exception e) {
    return new ResponseEntity<>(JWT_EXPIRE_ERR_MSG,HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(CustomJWTValidException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected ResponseEntity jwtNotValidException(HttpServletRequest request, Exception e) {
    return new ResponseEntity<>(JWT_NOT_VALID_ERR_MSG,HttpStatus.UNAUTHORIZED);
  }
}
