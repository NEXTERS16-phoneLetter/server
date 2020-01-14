package com.nexters.phoneletter.user.dto;

import com.nexters.phoneletter.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {

  private String email;
  private String phoneNumber;

  public User toEntity(){
    return User.builder()
            .email(email)
            .phoneNumber(phoneNumber)
            .build();
  }
}
