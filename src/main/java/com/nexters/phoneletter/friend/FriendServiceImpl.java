package com.nexters.phoneletter.friend;

import com.nexters.phoneletter.advice.exception.UserNotFoundException;
import com.nexters.phoneletter.user.domain.User;
import com.nexters.phoneletter.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {

  private static final Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);

  private final UserRepository userRepository;
  private final FriendRepository friendRepository;

  @Override
  public void addFriendByPhoneNumber(User user, FriendSaveRequestDto friendSaveRequestDto) {

    logger.info("addFriendByPhoneNumber()");
    Long userId = user.getId();
    String friendPhoneNumber = friendSaveRequestDto.getPhoneNumber();

    User friendInfo = userRepository.findByPhoneNumber(friendPhoneNumber)
        .orElseThrow(UserNotFoundException::new);
    Long friendId = friendInfo.getId();

    //TODO: 이미 친구인 경우, Block 되었던 경우 ?

    if (userId == friendId) {
      //TODO: throw Exception "자신의 핸드폰번호입니다 " exception 추가 해야함
      logger.warn("자신의 핸드폰 번호 error");
      throw new UserNotFoundException();
    }

    friendRepository.save(friendSaveRequestDto.saveFriend(userId, friendId));
    friendRepository.save(friendSaveRequestDto.saveFriend(friendId, userId));
  }

  @Override
  public void getFriendList() {

  }

  @Override
  @Transactional
  public void blockFriend(User user, FriendDeleteRequestDto friendDeleteRequestDto) {

    logger.info("blockFriend()");
    Long userId = user.getId();
    Long frieidId = friendDeleteRequestDto.getFriendId();

    FriendId build = FriendId.builder()
        .userId(userId)
        .friendId(frieidId)
        .build();

    friendRepository.update(build);

  }
}
