package com.nexters.phoneletter.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend,FriendId> {

  @Modifying
  @Query("update Friend f set f.isBlock = true WHERE f.friendId = :friendId")
  Integer update(@Param("friendId")FriendId friendId);

}
