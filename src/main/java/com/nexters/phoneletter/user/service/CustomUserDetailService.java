package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.advice.exception.UserNotFoundException;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    logger.info("loadUserByUsername()");
    return userRepository.findById(Long.parseLong(userId)).orElseThrow(UserNotFoundException::new);
  }
}
