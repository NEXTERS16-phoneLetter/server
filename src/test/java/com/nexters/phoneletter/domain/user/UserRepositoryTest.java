package com.nexters.phoneletter.domain.user;


import com.nexters.phoneletter.user.domain.Users;
import com.nexters.phoneletter.user.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @After
  public void cleanup() {
    /**
     이후 테스트 코드에 영향을 끼치지 않기 위해
     테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
     **/
    userRepository.deleteAll();
  }

  @Test
  public void 유저저장_불러오기() {
    //given
    userRepository.save(Users.builder()
            .email("tjddus1109@gmail.com")
            .phoneNumber("01097905388")
            .build());

    //when
    List<Users> usersList = userRepository.findAll();

    //then
    Users users = usersList.get(0);
    assertThat(users.getEmail(), is("tjddus1109@gmail.com"));
    assertThat(users.getPhoneNumber(), is("01097905388"));
  }

}