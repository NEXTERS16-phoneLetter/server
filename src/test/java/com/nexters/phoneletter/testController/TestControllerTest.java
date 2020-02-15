package com.nexters.phoneletter.testController;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Before
  public void setUp() {

  }

  @Test
  public void HelloURL_성공_테스트() throws Exception {
    // Given :

    // when : 사용자가 지정하는 동작을 설명
    String body = this.restTemplate.getForObject("/hello", String.class);

    // Then : 지정된 동작으로 인해 예상되는 변경 사항
    assertThat(body, equalToIgnoringCase("Hello World !"));
  }

  @Test
  public void TokenURL_TOKENNull_실패_테스트() throws Exception {
    //when
    String body = this.restTemplate.getForObject("/token", String.class);

    //Then
    assertThat(body, equalToIgnoringCase("Error가 발생 했습니다."));
  }
}