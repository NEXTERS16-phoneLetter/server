package com.nexters.phoneletter.user.service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.nexters.phoneletter.user.dto.UserAuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${letter.api_key}")
    private String api_key;
    @Value("${letter.api_secret}")
    private String api_secret;
    @Value("${letter.from}")
    private String from;

    @Override
    public boolean sendSms(String phoneNumber) {
        Message coolsms = new Message(api_key, api_secret);

        String code = Integer.toString((int)(Math.random() * 899999) + 100000); // 6자리 인증 코드 생성

        // key - phoneNumber, value - code
        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setPhoneNumber(phoneNumber);
        userAuthDto.setCode(code);
        redisTemplate.opsForValue().set(userAuthDto.getPhoneNumber(), userAuthDto.getCode());
        redisTemplate.expire(userAuthDto.getPhoneNumber(), 300, TimeUnit.SECONDS); // 캐시 유효 시간 5분

        HashMap<String, String> params = new HashMap<>();
        params.put("to", phoneNumber); // 수신번호
        params.put("from", from); // 발신번호
        params.put("text", "누구에게 code [" + code + "]"); // 문자내용
        params.put("type", "SMS"); // 문자 타입

        try {
            JSONObject result = coolsms.send(params); // 문자 전송, 결과 리턴
            // success_count 가 1인 경우에 문자 발송 성공
            if ("1".equals(result.get("success_count").toString())) {
                return true;
            }
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
        return false;
    }

    @Override
    public boolean phoneNumberCert(UserAuthDto userAuthDto) {
        if(userAuthDto.getCode().equals(redisTemplate.opsForValue().get(userAuthDto.getPhoneNumber()))){
            // 나중에 session을 통해 인증 여부 redis에 저장
            return true;
        }
        return false;
    }

}
