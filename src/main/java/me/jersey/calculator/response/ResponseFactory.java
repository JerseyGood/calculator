package me.jersey.calculator.response;

import lombok.extern.slf4j.Slf4j;
import me.jersey.calculator.exceptions.BadInputException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResponseFactory {

    public ResponseVo fromException(String token, int pos, Exception e, String stackString) {
        if (e instanceof BadInputException) {
            BadInputException bad = (BadInputException) e;
            String message = String.format("operator %s (position: %d): %s\n%s", token, pos, bad.getMessage(), formatStack(stackString));
            return ResponseVo.builder().code(ResponseCode.INPUT_ERROR).body(message).build();
        } else {
            log.error("Process token {} at position {} failed.", token, pos, e);
            return ResponseVo.builder().code(ResponseCode.INTERNAL_ERROR)
                .body(String.format("Internal Error processing \"%s\" at position: %d", token, pos)).build();
        }
    }

    private String formatStack(String stackString) {
        return "stack: " + stackString;
    }

    public ResponseVo ok(String stackString) {
        return ResponseVo.builder()
            .code(ResponseCode.OK)
            .body(formatStack(stackString)).build();
    }
}
