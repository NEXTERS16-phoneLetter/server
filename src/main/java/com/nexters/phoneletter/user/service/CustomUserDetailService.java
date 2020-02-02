package com.nexters.phoneletter.user.service;

import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userPK) throws UsernameNotFoundException {
    //TODO exception custom plz
    return userRepository.findById(Long.valueOf(userPK)).orElseThrow(RuntimeException::new);
  }
}
