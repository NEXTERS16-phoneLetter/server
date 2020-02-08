package com.nexters.phoneletter.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//결과가 단일건인 api를 담는 모델 Generic을 이용해 어떤 형태 값도 넣을 수 있게 구현, CommonResult 도 같이 출력
public class SingleResult<T> extends CommonResult {

  private T data;

}
