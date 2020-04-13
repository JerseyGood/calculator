package me.jersey.calculator.calculator;

import lombok.extern.slf4j.Slf4j;
import me.jersey.calculator.exceptions.InsufficientParameterException;
import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.number.NumberRepository;
import me.jersey.calculator.operation.OperationService;
import me.jersey.calculator.operator.Operator;
import me.jersey.calculator.operator.OperatorFactory;
import me.jersey.calculator.response.ResponseConsumer;
import me.jersey.calculator.response.ResponseFactory;
import me.jersey.calculator.response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class RpnCalculatorImpl implements RpnCalculator {

    @Autowired
    private NumberRepository numberRepo;
    @Autowired
    private OperatorFactory operatorFactory;
    @Autowired
    private OperationService operationService;
    @Autowired
    private ResponseConsumer responseConsumer;
    @Autowired
    private ResponseFactory responseFactory;

    @Override
    public RpnCalculator takeInput(String rawInput) {
        if (rawInput == null || rawInput.isEmpty()) return this;
        String[] inputs = rawInput.trim().split("\\s+");
        log.debug("Start processing input: {}", Arrays.toString(inputs));
        ResponseVo response = processInputs(inputs);
        log.debug("Finish processing input: {} with response {} ", Arrays.toString(inputs), response);
        responseConsumer.consume(response);
        return this;
    }

    private ResponseVo processInputs(String[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            String token = inputs[i];
            try {
                this.processToken(token);
            } catch (Exception e) {
                return responseFactory.fromException(token, i * 2 + 1, e, numberRepo.getStackString());
            }
        }
        return responseFactory.ok(numberRepo.getStackString());
    }

    private void processToken(String token) throws InsufficientParameterException {
        if (operatorFactory.has(token)) {
            Operator<NumberEntity> ope = operatorFactory.getOperator(token);
            operationService.applyOperator(ope);
        } else {
            operationService.pushNumber(NumberEntity.of(token));
        }
    }

}
