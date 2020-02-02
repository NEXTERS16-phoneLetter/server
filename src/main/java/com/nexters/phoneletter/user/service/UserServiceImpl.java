package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.config.security.JwtTokenProvider;
import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.dto.UserSaveRequestDto;
import com.nexters.phoneletter.user.dto.UserSigninRequestDto;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private JwtTokenProvider jwtTokenProvider;

  @Override
  @Transactional
  public User signUp(UserSaveRequestDto userSaveRequestDto) {
    return userRepository.save(userSaveRequestDto.toEntity(passwordEncoder));
  }

  @Override
  public String signIn(UserSigninRequestDto userSigninRequestDto) {
    //TODO: 이메일이 없는경우 custom Exception signInfailed
    User user = userRepository.findByEmail(userSigninRequestDto.getEmail())
        .orElseThrow(RuntimeException::new);

    //TODO: 비밀번호가 틀린 경우 custom Exception signInfailed
    if (!passwordEncoder.matches(userSigninRequestDto.getPassword(), user.getPassword())) {
      throw new RuntimeException();
    }

    return jwtTokenProvider.createToken(user.getId().toString(), user.getRoles());
  }

  @Override
  public User getUser() {
    return null;
  }

}
