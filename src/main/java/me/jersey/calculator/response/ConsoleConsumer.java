package me.jersey.calculator.response;

import org.springframework.stereotype.Component;

@Component
public class ConsoleConsumer implements ResponseConsumer {

    @Override
    public void consume(ResponseVo response) {
        System.out.println(response.getBody());
    }
}
