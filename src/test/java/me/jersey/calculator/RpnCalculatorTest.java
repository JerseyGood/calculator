package me.jersey.calculator;

import me.jersey.calculator.calculator.RpnCalculator;
import me.jersey.calculator.number.NumberRepository;
import me.jersey.calculator.operation.OperationLogRepository;
import me.jersey.calculator.response.ResponseConsumer;
import me.jersey.calculator.response.ResponseVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@SpringBootTest
class RpnCalculatorTest {

    @Captor
    ArgumentCaptor<ResponseVo> responseCaptor;
    @Autowired
    private RpnCalculator calc;
    @Autowired
    private OperationLogRepository logRepo;
    @Autowired
    private NumberRepository numberRepo;
    @MockBean
    private ResponseConsumer mockConsumer;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        logRepo.clear();
    }

    @Test
    public void testEmptyInput() {
        calc.takeInput("");
        Mockito.verify(mockConsumer, never().description("Empty input should has no affect at all"))
                .consume(any());

    }

    @Test
    public void testPrintStack() {
        List<String> tests = Arrays.asList(
                "5 2",
                "1 2 3",
                "1.99 2.0000001"
        );
        for (String test : tests) {
            calc.takeInput(test);
            Mockito.verify(mockConsumer, Mockito.atLeastOnce()).consume(responseCaptor.capture());
            ResponseVo response = responseCaptor.getValue();
            assertEquals("stack: " + test, response.getBody(), "Calculator Stack is not right for input: " + test);
            numberRepo.clear();
            logRepo.clear();
        }
    }

    @Test
    public void testClear() {
        List<String[]> tests = Arrays.asList(
                new String[]{"clear", ""},
                new String[]{"5 2", "5 2"},
                new String[]{"5 2", "clear", ""},
                new String[]{"1 2 3 clear", "4 5 * clear", ""}
        );
        testSuite(tests);
    }

    @Test
    public void testNormaltOperator() {
        List<String[]> tests = Arrays.asList(
                new String[]{"5 2 +", "7"},
                new String[]{"5 2 1 - -", "4"},
                new String[]{"5 2 1 - -", "5 *", "20"},
                new String[]{"5 2 1 - -", "5 *", "5 /", "4"},
                new String[]{"5 2 1 - -", "5 *", "5 /", "sqrt", "2"},
                new String[]{"2 sqrt", "1.4142135623"},
                new String[]{"2 sqrt", "clear 9 sqrt", "3"},
                new String[]{"1 2 3 4 5", "1 2 3 4 5"},
                new String[]{"1 2 3 4 5", "* * * *", "120"}
        );
        testSuite(tests);
    }

    @Test
    public void testUndoOperator() {
        List<String[]> tests = Arrays.asList(
                new String[]{"5 4 3 2", "5 4 3 2"},
                new String[]{"5 4 3 2", "undo", "5 4 3"},
                new String[]{"5 4 3 2", "undo undo *", "20"},
                new String[]{"5 4 3 2", "undo undo *", "5 *", "100"},
                new String[]{"5 4 3 2", "undo undo *", "5 *", "undo", "20 5"}
        );
        testSuite(tests);
    }

    @Test
    public void testInsufficientParameter() {
        String input = "1 2 3 * 5 + * * 6 5";
        String expected = "operator * (position: 15): insufficient parameters\n" + "stack: 11";
        calc.takeInput(input);
        Mockito.verify(mockConsumer, Mockito.atLeastOnce()).consume(responseCaptor.capture());
        ResponseVo response = responseCaptor.getValue();
        assertEquals(expected, response.getBody());
    }

    /**
     * @param tests the last element each test (String[]) is expected message
     */
    private void testSuite(List<String[]> tests) {
        for (String[] test : tests) {
            for (int i = 0; i < test.length - 1; i++) {
                calc.takeInput(test[i]);
            }
            Mockito.verify(mockConsumer, Mockito.atLeastOnce()).consume(responseCaptor.capture());
            // we are only interested in last call
            ResponseVo response = responseCaptor.getValue();
            assertEquals("stack: " + test[test.length - 1], response.getBody(),
                    Arrays.toString(test));
            // clean up
            numberRepo.clear();
            logRepo.clear();
        }
    }
}
