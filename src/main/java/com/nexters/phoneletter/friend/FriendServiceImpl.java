package com.nexters.phoneletter.friend;

import com.nexters.phoneletter.advice.exception.CustomUserNotFoundException;
import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {

  private final UserRepository userRepository;
  private final FriendRepository friendRepository;

  @Override
  public void addFriendByPhoneNumber(User user, FriendSaveRequestDto friendSaveRequestDto) {
    Long userId = user.getId();
    String friendPhoneNumber = friendSaveRequestDto.getPhoneNumber();

    User friendInfo = userRepository.findByPhoneNumber(friendPhoneNumber)
        .orElseThrow(CustomUserNotFoundException::new);
    Long friendId = friendInfo.getId();

    //TODO: 이미 친구인 경우, Block 되었던 경우 ?

    if (userId == friendId) {
      //TODO: throw Exception "자신의 핸드폰번호입니다 " exception 추가 해야함
      throw new CustomUserNotFoundException();
    }

    friendRepository.save(friendSaveRequestDto.saveFriend(userId, friendId));
    friendRepository.save(friendSaveRequestDto.saveFriend(friendId, userId));
  }

  @Override
  public void getFriendList() {

  }

  @Override
  public void deleteFriend() {

  }
}
