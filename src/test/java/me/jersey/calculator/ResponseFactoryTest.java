package me.jersey.calculator;

import me.jersey.calculator.exceptions.InsufficientParameterException;
import me.jersey.calculator.response.ResponseCode;
import me.jersey.calculator.response.ResponseFactory;
import me.jersey.calculator.response.ResponseVo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseFactoryTest {
    ResponseFactory responseFactory = new ResponseFactory();

    @Test
    public void testOkResponse() {
        String stackString = "1 2 3 4";
        ResponseVo vo = responseFactory.ok(stackString);
        assertEquals(ResponseCode.OK, vo.getCode());
        assertEquals("stack: " + stackString, vo.getBody());
    }

    @Test
    public void testExceptionResponse() {
        String stackString = "stack: 1 *";
        ResponseVo vo = responseFactory.fromException("*", 3, new InsufficientParameterException(), stackString);
        assertEquals(ResponseCode.INPUT_ERROR, vo.getCode());
        String expected = String.format("operator * (position: 3): insufficient parameters\nstack: %s", stackString);
        assertEquals(expected, vo.getBody());
        assertEquals(ResponseCode.INTERNAL_ERROR,
                responseFactory.fromException("*", 3, new RuntimeException("Test Internal Error: NPE"), stackString).getCode());
    }
}
