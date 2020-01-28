package com.nexters.phoneletter.domain.user;


import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

//  @After
//  public void cleanup() {
//    /**
//     이후 테스트 코드에 영향을 끼치지 않기 위해
//     테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
//     **/
//    userRepository.deleteAll();
//  }

  @Test
  public void 유저저장_불러오기() {
    //given
    userRepository.save(User.builder()
        .email("tjddus1109@gmail.com")
        .phone1("010")
        .phone2("9790")
        .phone3("5388")
        .build());

    //when
    List<User> userList = userRepository.findAll();

    //then
    User user = userList.get(0);
    assertThat(user.getEmail(), is("tjddus1109@gmail.com"));
    assertThat(user.getPhone1(), is("010"));
    assertThat(user.getPhone2(), is("9790"));
    assertThat(user.getPhone3(), is("5388"));
  }

}