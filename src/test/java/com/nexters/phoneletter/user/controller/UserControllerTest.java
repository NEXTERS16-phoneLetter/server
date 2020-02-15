package com.nexters.phoneletter.user.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;

import com.nexters.phoneletter.user.domain.User;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = {"test"})
@Transactional
public class UserControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Before
  public void setUp() throws Exception {

    System.out.println("Before()::User Table에 test유저 추가하기 ");
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    Map map = new HashMap<String, String>();
    map.put("Content-Type", "application/json");

    headers.setAll(map);

    Map req_payload = new HashMap();
    req_payload.put("email", "test@gmail.com");
    req_payload.put("phoneNumber", "01000000000");
    req_payload.put("password", "test");

    HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

    // When
    this.restTemplate.postForEntity("/users/signup", request, String.class);

    System.out.println("Before()::종료");
  }

  @Test
  public void 회원가입_성공_테스트() throws Exception {

    //Given : header 및 body 추가
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    Map map = new HashMap<String, String>();
    map.put("Content-Type", "application/json");

    headers.setAll(map);

    Map req_payload = new HashMap();
    req_payload.put("email", "tjddus1109@naver.com");
    req_payload.put("phoneNumber", "01097905388");
    req_payload.put("password", "1234");

    HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

    // When
    ResponseEntity<User> userResponseEntity = this.restTemplate
        .postForEntity("/users/signup", request, User.class);

    // Then
    assertThat(userResponseEntity.getStatusCode(), is(HttpStatus.CREATED));
    assertThat(userResponseEntity.getBody(), instanceOf(User.class));
  }

  @Test
  public void 회원가입_실패_테스트_() throws Exception {

    //Given : header 및 body 추가
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    Map map = new HashMap<String, String>();
    map.put("Content-Type", "application/json");

    headers.setAll(map);

    Map req_payload = new HashMap();
    req_payload.put("email", "tjddus1109@naver.com");
    req_payload.put("phoneNumber", "01097905389");
    req_payload.put("password", "1234");

    HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

    // When
    ResponseEntity<String> userResponseEntity = this.restTemplate
        .postForEntity("/users/signup", request, String.class);

    // Then
    assertThat(userResponseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    assertThat(userResponseEntity.getBody(), equalToIgnoringCase("회원가입에 실패 하였습니다."));
  }

  @Test
  public void 로그인_성공_테스트() throws Exception {
    //Given : header 및 body 추가
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    Map map = new HashMap<String, String>();
    map.put("Content-Type", "application/json");

    headers.setAll(map);

    Map req_payload = new HashMap();
    req_payload.put("email", "test@gmail.com");
    req_payload.put("password", "test");

    HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

    // When
    ResponseEntity<String> userResponseEntity = this.restTemplate
        .postForEntity("/users/signin", request, String.class);

    // Then : jwt token String인지 ,
    assertThat(userResponseEntity.getStatusCode(), is(HttpStatus.OK));
    assertThat(userResponseEntity.getBody(), instanceOf(String.class));
  }

  @Test
  public void 로그인_실패_테스트() throws Exception {
    //Given : header 및 body 추가
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    Map map = new HashMap<String, String>();
    map.put("Content-Type", "application/json");

    headers.setAll(map);

    Map req_payload = new HashMap();
    req_payload.put("email", "tjddus1109@gmail.com");
    req_payload.put("password", "test");

    HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

    // When
    ResponseEntity<String> userResponseEntity = this.restTemplate
        .postForEntity("/users/signin", request, String.class);

    // Then : jwt token String인지 ,
    assertThat(userResponseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    assertThat(userResponseEntity.getBody(), equalToIgnoringCase("로그인에 실패 하였습니다."));
  }

}