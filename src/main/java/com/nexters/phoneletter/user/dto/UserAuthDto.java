package com.nexters.phoneletter.user.dto;

import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
public class UserAuthDto {
    private String phoneNumber;
    private String code;
}
