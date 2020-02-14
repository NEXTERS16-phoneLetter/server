package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.advice.exception.KakaoUserNotFoundException;
import com.nexters.phoneletter.advice.exception.PasswordNotMatchException;
import com.nexters.phoneletter.advice.exception.SignUpFailException;
import com.nexters.phoneletter.advice.exception.UserNotFoundException;
import com.nexters.phoneletter.config.security.JwtTokenProvider;
import com.nexters.phoneletter.user.domain.KakaoUser;
import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.KakaoUserRequestDto;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.dto.UserSigninRequestDto;
import com.nexters.phoneletter.user.repository.KakaoUserRepository;
import com.nexters.phoneletter.user.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private KakaoUserRepository kakaoUserRepository;

  private PasswordEncoder passwordEncoder;
  private JwtTokenProvider jwtTokenProvider;


  @Override
  @Transactional
  public User signUp(UserSaveRequestDto userSaveRequestDto) {

    log.info("signUp");

    User user = null;
    try {
      user = userRepository.save(userSaveRequestDto.toEntity(passwordEncoder));
    } catch (Exception e) {
      log.warn("SignUpFailException");
      throw new SignUpFailException();
    }
    return user;
  }

  @Override
  @Transactional
  public String signIn(UserSigninRequestDto userSigninRequestDto) {

    log.info("signIn");
    User user = userRepository.findByEmail(userSigninRequestDto.getEmail())
        .orElseThrow(UserNotFoundException::new);

    if (!passwordEncoder.matches(userSigninRequestDto.getPassword(), user.getPassword())) {
      log.warn("PasswordNotMatchException");
      throw new PasswordNotMatchException();
    }
    return jwtTokenProvider.createToken(user.getId().toString(), user.getRoles());
  }

  @Override
  @Transactional
  public String kakaoLogin(KakaoUserRequestDto kakaoUserRequestDto) {

    log.info("kakaoLogin");

    String kakaoId = kakaoUserRequestDto.getKakaoId();

    KakaoUser kakaoUser = kakaoUserRepository.findByKakaoId(kakaoId)
        .orElseThrow(KakaoUserNotFoundException::new);

    User user = userRepository.findById(kakaoUser.getUserId())
        .orElseThrow(UserNotFoundException::new);
    Hibernate.initialize(user.getRoles());

    return jwtTokenProvider.createToken(user.getId().toString(), user.getRoles());
  }


  @Override
  public String kakaoRegister(KakaoUserRequestDto kakaoUserRequestDto) {

    log.info("kakaoRegister");

    User saveUser = User.builder()
        .phoneNumber(kakaoUserRequestDto.getPhoneNumber())
        .nickname(kakaoUserRequestDto.getNickname())
        .profile(kakaoUserRequestDto.getProfile())
        .type("KAKAO")
        .roles(Collections.singletonList("ROLE_USER"))
        .build();

    Optional.ofNullable(saveUser).map(User::getPhoneNumber).orElseThrow(SignUpFailException::new);

    User user = userRepository.save(saveUser);

    KakaoUser saveKakaoUser = KakaoUser.builder()
        .userId(user.getId())
        .kakaoId(kakaoUserRequestDto.getKakaoId())
        .build();

    kakaoUserRepository.save(saveKakaoUser);

    return jwtTokenProvider.createToken(user.getId().toString(), user.getRoles());
  }

  @Override
  public User getUser() {
    return null;
  }

}


