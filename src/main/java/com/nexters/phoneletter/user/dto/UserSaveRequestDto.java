package com.nexters.phoneletter.user.dto;

import com.nexters.phoneletter.user.domain.User;
import java.util.Collections;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {


  @NotBlank(message = "메일을 작성해주세요.")
  @Email(message = "이메일 양식을 지켜주세요.")
  private String email;

  @NotBlank(message = "전화번호를 작성해주세요.")
  @Pattern(regexp = "[0-9]{10,11}", message = "10-11자리의 숫자만 입력가능합니다.")
  private String phoneNumber;

  @NotBlank(message = "비밀번를 작성해주세요.")
  private String password;

  public User toEntity(PasswordEncoder passwordEncoder) {
    return User.builder()
        .email(email)
        .phoneNumber(phoneNumber)
        .password(passwordEncoder.encode(password))
        .roles(Collections.singletonList("ROLE_USER"))
        .build();
  }
}
