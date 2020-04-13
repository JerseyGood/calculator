package me.jersey.calculator;

import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.number.NumberRepository;
import me.jersey.calculator.operation.OperationLogRepository;
import me.jersey.calculator.operation.OperationLogVo;
import me.jersey.calculator.operation.OperationService;
import me.jersey.calculator.operation.OperationServiceImpl;
import me.jersey.calculator.operator.Operator;
import me.jersey.calculator.operator.impl.AddOperator;
import me.jersey.calculator.operator.impl.UndoOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class OperationServiceImplTest {

    private OperationService operationService;
    @Mock
    private OperationLogRepository<NumberEntity> mockOperationLogRepo;
    @Mock
    private NumberRepository mockNumberRepo;

    @Captor
    ArgumentCaptor<NumberEntity> numberArgCaptor;
    @Captor
    ArgumentCaptor<List<NumberEntity>> numberListArg;
    @Captor
    ArgumentCaptor<OperationLogVo<NumberEntity>> operationLogCaptor;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        operationService = new OperationServiceImpl(mockNumberRepo, mockOperationLogRepo);
    }

    @Test
    public void testPushNumebr() {
        NumberEntity number1 = NumberEntity.of("20");
        operationService.pushNumber(number1);

        Mockito.verify(mockNumberRepo).push(numberArgCaptor.capture());
        Assertions.assertEquals(number1, numberArgCaptor.getValue(),
                String.format("Number %s should be pushed to number repo", number1));

        Mockito.verify(mockOperationLogRepo).addLog(operationLogCaptor.capture());
        OperationLogVo<NumberEntity> expectLog = OperationLogVo.<NumberEntity>builder()
                .poped(Collections.emptyList()).pushed(Arrays.asList(number1)).build();
        Assertions.assertEquals(expectLog, operationLogCaptor.getValue(),
                "An operation log should be recorded.");
    }

    @Test
    public void testApplyOperator() {
        // mock an unlimited stack
        when(mockNumberRepo.size()).thenReturn(Integer.MAX_VALUE);
        when(mockNumberRepo.pop()).thenReturn(NumberEntity.of("2"));

        Operator add = new AddOperator();
        operationService.applyOperator(add);
        verify(mockNumberRepo, times(add.paramSize()).description("should pop twice when applying + operator")).pop();
        verify(mockNumberRepo).addAll(numberListArg.capture());
        Assertions.assertEquals(Arrays.asList(NumberEntity.of("4")), numberListArg.getValue(),
                "Should add [4] to number repo when applying + operator on [2 2] ");

        Mockito.verify(mockOperationLogRepo).addLog(operationLogCaptor.capture());
        OperationLogVo<NumberEntity> expectLog = OperationLogVo.<NumberEntity>builder()
                .poped(Arrays.asList(NumberEntity.of("2"), NumberEntity.of("2")))
                .pushed(Arrays.asList(NumberEntity.of("4"))).build();
        Assertions.assertEquals(expectLog, operationLogCaptor.getValue(),
                "An operation log should be recorded.");
    }

    @Test
    public void testApplyUndoOperator() {
        List<NumberEntity> poped = Arrays.asList(NumberEntity.of("2"), NumberEntity.of("3"));
        OperationLogVo<NumberEntity> log = OperationLogVo.<NumberEntity>builder()
                .poped(poped)
                .pushed(Arrays.asList(NumberEntity.of("5"))).build();
        when(mockOperationLogRepo.removeLastLog()).thenReturn(log);

        Operator undo = new UndoOperator();
        operationService.applyOperator(undo);

        verify(mockNumberRepo, description("should pop what have ben pushed in last operation")).pop();
        verify(mockNumberRepo).addAll(numberListArg.capture());
        Assertions.assertEquals(poped, numberListArg.getValue(),
                "Should add back what have been poped in last operation.");
        verify(mockOperationLogRepo, never().description("Undo operation should not add new undolog")).addLog(any());
    }
}
