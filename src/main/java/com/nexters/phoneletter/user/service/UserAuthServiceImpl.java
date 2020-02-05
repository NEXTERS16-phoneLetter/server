package com.nexters.phoneletter.user.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.nexters.phoneletter.user.dto.UserAuthDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
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

    private static final String url = "https://api.coolsms.co.kr/sms/2/send";

    @Override
    public void sendSmsRestTemplate(String phoneNumber) {
        Message coolsms = new Message(api_key, api_secret);

        UserAuthDto userAuthDto = UserAuthDto.builder()
                .code(generateVerificationCode())
                .phoneNumber(phoneNumber)
                .build();

        // TODO key - phoneNumber, value - code, 캐시 유효 시간 5분
        redisTemplate.opsForValue().set(userAuthDto.getPhoneNumber(), userAuthDto.getCode());
        redisTemplate.expire(userAuthDto.getPhoneNumber(), 300, TimeUnit.SECONDS);
        String text = "누구에게 code [" + userAuthDto.getCode() + "]";

        try {
            String salt = coolsms.getSalt();
            String timestamp = coolsms.getTimestamp();

            DefaultHttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("api_key", api_key));
            nvps.add(new BasicNameValuePair("salt", salt));
            nvps.add(new BasicNameValuePair("signature", coolsms.getSignature(api_secret, salt, timestamp)));
            nvps.add(new BasicNameValuePair("timestamp", timestamp));
            nvps.add(new BasicNameValuePair("from", from));
            nvps.add(new BasicNameValuePair("to", phoneNumber));
            nvps.add(new BasicNameValuePair("text", text));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

            HttpResponse response = httpclient.execute(httpPost);

            System.out.println(response.getStatusLine());
            System.out.println(response.getEntity());
            System.out.println(response.getLocale());

        } catch (CoolsmsException | UnsupportedEncodingException e) {
            log.error("error msg : ", e.getMessage());
        } catch (ClientProtocolException e) {
            log.error("error msg : ", e.getMessage());
        } catch (IOException e) {
            log.error("error msg : ", e.getMessage());
        }
    }

    @Override
    public boolean isMatchVerifyCode(UserAuthDto userAuthDto) {
        if(userAuthDto.getCode().equals(redisTemplate.opsForValue().get(userAuthDto.getPhoneNumber()))){
            // TODO 나중에 session을 통해 인증 여부 redis에 저장
            return true;
        }
        return false;
    }

    private static String generateVerificationCode() { // 6자리 인증 코드 생성
        return Integer.toString((int)(Math.random() * 899999) + 100000);
    }
}
