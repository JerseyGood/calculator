package me.jersey.calculator.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseVo {
    ResponseCode code;
    String body;
}
