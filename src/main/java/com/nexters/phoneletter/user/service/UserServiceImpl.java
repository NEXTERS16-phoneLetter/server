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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private JwtTokenProvider jwtTokenProvider;

  @Override
  @Transactional
  public User signUp(UserSaveRequestDto userSaveRequestDto) {
    User user = null;
    try {
      user = userRepository.save(userSaveRequestDto.toEntity(passwordEncoder));
    }catch(Exception e){
      throw new SignUpFailException();
    }
    return user;
  }

  @Override
  @Transactional
  public String signIn(UserSigninRequestDto userSigninRequestDto) {
    User user = userRepository.findByEmail(userSigninRequestDto.getEmail())
        .orElseThrow(UserNotFoundException::new);

    if (!passwordEncoder.matches(userSigninRequestDto.getPassword(), user.getPassword())) {
      throw new PasswordNotMatchException();
    }
    return jwtTokenProvider.createToken(user.getId().toString(), user.getRoles());
  }

  @Override
  public User getUser() {
    return null;
  }

}


