package com.nexters.phoneletter.friend;

import com.nexters.phoneletter.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/friends", description = "친구 관리 api")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/friends")
public class FriendController {

  private final FriendServiceImpl friendService;

  @ApiOperation(value = "핸드폰번호로 친구 추가 하기")
  @PostMapping()
  public ResponseEntity addFriendByPhoneNumber(
      @RequestBody @Valid FriendSaveRequestDto friendSaveRequestDto,
      @AuthenticationPrincipal User user) {

    friendService.addFriendByPhoneNumber(user, friendSaveRequestDto);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @ApiOperation(value = "친구 차단 하기")
  @DeleteMapping()
  public ResponseEntity blockFriend(
      @RequestBody @Valid FriendDeleteRequestDto friendDeleteRequestDto,
      @AuthenticationPrincipal User user) {

    friendService.blockFriend(user, friendDeleteRequestDto);
    return new ResponseEntity(HttpStatus.OK);
  }

}
