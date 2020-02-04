package com.nexters.phoneletter.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//결과가 여러건인 api를 담는 모델
public class ListResult<T> extends CommonResult {

  private List<T> list;

}
