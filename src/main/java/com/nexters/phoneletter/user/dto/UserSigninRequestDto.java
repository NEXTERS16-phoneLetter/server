package com.nexters.phoneletter.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSigninRequestDto {

  @NotBlank(message = "메일을 작성해주세요.")
  @Email(message = "이메일 양식을 지켜주세요.")
  private String email;

  @NotBlank(message = "비밀번를 작성해주세요.")
  private String password;

}
