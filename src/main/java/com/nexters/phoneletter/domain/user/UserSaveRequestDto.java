package com.nexters.phoneletter.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {

  private String email;
  private String phoneNumber;

  public Users toEntity(){
    return Users.builder()
            .email(email)
            .phoneNumber(phoneNumber)
            .build();
  }
}
