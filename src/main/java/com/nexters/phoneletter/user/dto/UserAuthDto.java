package com.nexters.phoneletter.user.dto;

import lombok.Data;

@Data
public class UserAuthDto {
    private String phoneNumber;
    private String code;
}
