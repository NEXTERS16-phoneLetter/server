package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.advice.exception.PasswordNotMatchException;
import com.nexters.phoneletter.advice.exception.SignUpFailException;
import com.nexters.phoneletter.advice.exception.UserNotFoundException;
import com.nexters.phoneletter.config.security.JwtTokenProvider;
import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.dto.UserSigninRequestDto;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private JwtTokenProvider jwtTokenProvider;

  @Override
  @Transactional
  public User signUp(UserSaveRequestDto userSaveRequestDto) {

    logger.info("signUp");

    User user = null;
    try {
      user = userRepository.save(userSaveRequestDto.toEntity(passwordEncoder));
    }catch(Exception e){
      logger.warn("SignUpFailException");
      throw new SignUpFailException();
    }
    return user;
  }

  @Override
  @Transactional
  public String signIn(UserSigninRequestDto userSigninRequestDto) {

    logger.info("signIn");
    User user = userRepository.findByEmail(userSigninRequestDto.getEmail())
        .orElseThrow(UserNotFoundException::new);

    if (!passwordEncoder.matches(userSigninRequestDto.getPassword(), user.getPassword())) {
      logger.warn("PasswordNotMatchException");
      throw new PasswordNotMatchException();
    }
    return jwtTokenProvider.createToken(user.getId().toString(), user.getRoles());
  }

  @Override
  public User getUser() {
    return null;
  }

}


