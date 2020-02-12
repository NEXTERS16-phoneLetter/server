package com.nexters.phoneletter.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserRequestDto {

  private String kakaoId;

  @Pattern(regexp = "[0-9]{10,11}", message = "10-11자리의 숫자만 입력가능합니다.")
  private String phoneNumber;

  private String nickname;

  private String profile;

}
