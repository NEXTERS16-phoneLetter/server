package com.nexters.phoneletter.user.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nexters.phoneletter.user.dto.UserAuthDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import net.nurigo.java_sdk.api.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private DefaultHttpClient httpClient = new DefaultHttpClient();

    @Value("${letter.api_key}")
    private String api_key;
    @Value("${letter.api_secret}")
    private String api_secret;
    @Value("${letter.from}")
    private String from;

    private static final String AUTH_RESULT = "verified";
    private static final String COOL_SMS_URL= "https://api.coolsms.co.kr/sms/2/send";

    @SneakyThrows({UnsupportedEncodingException.class, IOException.class, CoolsmsException.class})
    @Override
    public void sendSms(UserAuthDto userAuthDto){
        Message coolsms = new Message(api_key, api_secret);

        userAuthDto = UserAuthDto.builder()
                .phoneNumber(userAuthDto.getPhoneNumber())
                .code(generateVerificationCode())
                .build();

        // TODO key - phoneNumber, value - code, 캐시 유효 시간 5분
        redisTemplate.opsForValue().set(userAuthDto.getPhoneNumber(), userAuthDto.getCode());
        redisTemplate.expire(userAuthDto.getPhoneNumber(), 5, TimeUnit.MINUTES);
        String text = "누구에게 인증번호 [" + userAuthDto.getCode() + "]를 입력해 주세요.";
        String salt = coolsms.getSalt();
        String timestamp = coolsms.getTimestamp();

        List<NameValuePair> nvps = Stream.<NameValuePair>builder()
                .add(new BasicNameValuePair("api_key", api_key))
                .add(new BasicNameValuePair("salt", salt))
                .add(new BasicNameValuePair("signature", coolsms.getSignature(api_secret, salt, timestamp)))
                .add(new BasicNameValuePair("timestamp", timestamp))
                .add(new BasicNameValuePair("from", from))
                .add(new BasicNameValuePair("to", userAuthDto.getPhoneNumber()))
                .add(new BasicNameValuePair("text", text))
                .build()
                .collect(Collectors.toList());

        HttpPost httpPost = new HttpPost(COOL_SMS_URL);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

        HttpResponse response = httpClient.execute(httpPost);
        log.info(response.toString());
    }

    @Override
    public boolean isMatchVerifyCode(UserAuthDto userAuthDto, HttpServletRequest request) {
        if(userAuthDto.getCode().equals(redisTemplate.opsForValue().get(userAuthDto.getPhoneNumber()))){
            HttpSession session = request.getSession();
            session.setAttribute(userAuthDto.getPhoneNumber(), "verified");
            return true;
        }
        return false;
    }

    @Override
    public boolean isVerifiedPhoneNumber(String phoneNumber, HttpServletRequest request){
        HttpSession session = request.getSession();
        return AUTH_RESULT.equals(session.getAttribute(phoneNumber));
    }

    private static String generateVerificationCode() { // 6자리 인증 코드 생성
        return Integer.toString((int)(Math.random() * 899999) + 100000);
    }
}