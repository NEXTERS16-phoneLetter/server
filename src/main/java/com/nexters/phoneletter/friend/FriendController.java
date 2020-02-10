package com.nexters.phoneletter.friend;

import com.nexters.phoneletter.response.CommonResult;
import com.nexters.phoneletter.response.ResponseService;
import com.nexters.phoneletter.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Api(value = "/friends", description = "친구 관리 api")
@RestControllerAdvice
@AllArgsConstructor
@RequestMapping(value = "/friends")
public class FriendController {

  private final FriendServiceImpl friendService;
  private final ResponseService responseService;

  @ApiOperation(value = "핸드폰번호로 친구 추가 하기")
  @PostMapping()
  public CommonResult AddFriendByPhoneNumber(@RequestBody FriendSaveRequestDto friendSaveRequestDto,
      @AuthenticationPrincipal User user) {

    friendService.addFriendByPhoneNumber(user, friendSaveRequestDto);
    return responseService.getSuccessResult();
  }

}
