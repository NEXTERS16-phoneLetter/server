package com.nexters.phoneletter.user.dto;

import com.nexters.phoneletter.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {

  @NotBlank(message = "메일을 작성해주세요.")
  @Email(message = "이메일 양식을 지켜주세요.")
  private String email;

  @NotBlank(message = "전화번호를 작성해주세요.")
  @Pattern(regexp = "[0-9]{10,11}", message = "10-11자리의 숫자만 입력가능합니다.")
  private String phoneNumber;

  public User toEntity() {
    String[] phones = parsePhoneNumber();
    return User.builder()
        .email(email)
        .phone1(phones[0])
        .phone2(phones[1])
        .phone3(phones[2])
        .build();
  }

  private String[] parsePhoneNumber(){
    String[] phones = new String[3];
    int mid = phoneNumber.length() == 11 ? 7:6;
    phones[0] = phoneNumber.substring(0,3);
    phones[1] = phoneNumber.substring(3,mid);
    phones[2] = phoneNumber.substring(mid,phoneNumber.length());
    return phones;
  }

}
