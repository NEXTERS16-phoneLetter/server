package com.nexters.phoneletter.friend;

import com.nexters.phoneletter.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"친구 REST API"})
@SwaggerDefinition(tags = {
    @Tag(name = "친구 REST API", description = "친구 관리 REST API")
})
@RestController
@AllArgsConstructor
@RequestMapping(value = "/friends")
public class FriendController {

  private final FriendServiceImpl friendService;

  @ApiOperation(value = "핸드폰번호로 친구 추가 하기")
//  @ApiImplicitParam(name = "phoneNumber",value="친구 추가 할 핸드폰 번호",required = true,dataType = "string",paramType = "Json")
  @PostMapping()
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "친구 추가하기 성공"),
      @ApiResponse(code = 400, message = "친구 추가하기 실패")
  })
  public ResponseEntity addFriendByPhoneNumber(
      @RequestBody @Valid FriendSaveRequestDto friendSaveRequestDto,
      @AuthenticationPrincipal User user) {

    log.info("addFriendByPhoneNumber()");
    friendService.addFriendByPhoneNumber(user, friendSaveRequestDto);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @ApiOperation(value = "친구 차단 하기")
  @DeleteMapping()
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "친구 차단하기 성공"),
      @ApiResponse(code = 400, message = "친구 차단하기 실패")
  })
  public ResponseEntity blockFriend(
      @RequestBody @Valid FriendDeleteRequestDto friendDeleteRequestDto,
      @AuthenticationPrincipal User user) {

    log.info("blockFriend()");
    friendService.blockFriend(user, friendDeleteRequestDto);
    return new ResponseEntity(HttpStatus.OK);
  }

}
