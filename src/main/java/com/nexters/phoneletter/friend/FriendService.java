package com.nexters.phoneletter.friend;

import com.nexters.phoneletter.user.domain.User;

public interface FriendService {

  public void addFriendByPhoneNumber(User user,FriendSaveRequestDto friendSaveRequestDto);

  public void getFriendList();

  public void deleteFriend();

}
