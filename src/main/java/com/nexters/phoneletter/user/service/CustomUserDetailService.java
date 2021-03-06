package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.advice.exception.UserNotFoundException;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

    log.info("loadUserByUsername()");
    return userRepository.findById(Long.parseLong(userId)).orElseThrow(UserNotFoundException::new);
  }
}
