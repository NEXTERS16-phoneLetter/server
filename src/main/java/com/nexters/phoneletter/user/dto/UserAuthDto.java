package com.nexters.phoneletter.user.dto;

import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
@Setter
public class UserAuthDto {
    private String phoneNumber;
    private String code;
}
