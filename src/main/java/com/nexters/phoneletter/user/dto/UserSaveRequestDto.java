package com.nexters.phoneletter.user.dto;

import com.nexters.phoneletter.user.domain.Users;
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
