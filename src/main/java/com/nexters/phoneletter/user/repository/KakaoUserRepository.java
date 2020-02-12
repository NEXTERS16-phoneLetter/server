package com.nexters.phoneletter.user.repository;

import com.nexters.phoneletter.user.domain.KakaoUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoUserRepository extends JpaRepository<KakaoUser,Long> {

  Optional<KakaoUser> findByKakaoId(String kakaoId);

}
