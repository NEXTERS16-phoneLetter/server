package com.nexters.phoneletter.friend;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendSaveRequestDto {

  static final private Boolean FRIEND_NOT_BLOCK = false;

  @NotBlank(message = "전화번호를 작성해주세요.")
  @Pattern(regexp = "[0-9]{10,11}", message = "10-11자리의 숫자만 입력가능합니다.")
  private String phoneNumber;

  public Friend saveFriend(Long userId, Long friendId) {
    FriendId friendIdBuild = FriendId.builder()
        .userId(userId)
        .friendId(friendId)
        .build();

    return Friend.builder()
        .friendId(friendIdBuild)
        .isBlock(FRIEND_NOT_BLOCK)
        .build();
  }

}
