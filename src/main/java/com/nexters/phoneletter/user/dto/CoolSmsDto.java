package com.nexters.phoneletter.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CoolSmsDto {
    // 변수명과 다르게 Jackson에서 이름을 다르게 할 경우 @JsonProperty 어노테이션 사용
    @JsonProperty("api_key")
    private String apiKey;
    private String timestamp;
    private String salt;
    private String signature;
    private String to;
    private String from;
    private String text;
}
