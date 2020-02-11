package com.nexters.phoneletter.friend;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendDeleteRequestDto {

  @NotNull()
  private Long friendId;

}
