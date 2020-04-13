package me.jersey.calculator.operation;

import me.jersey.calculator.exceptions.InsufficientParameterException;
import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.number.NumberRepository;
import me.jersey.calculator.operator.Operator;
import me.jersey.calculator.operator.impl.UndoOperator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class OperationServiceImpl implements OperationService {
    private final NumberRepository numberRepo;
    private final OperationLogRepository<NumberEntity> operationLogRepository;

    public OperationServiceImpl(NumberRepository numberRepo, OperationLogRepository<NumberEntity> operationLogRepository) {
        this.numberRepo = numberRepo;
        this.operationLogRepository = operationLogRepository;
    }

    /**
     * e.g. popN([1,2,3],2) changes stack to [1] and returns [2,3]
     */
    private List<NumberEntity> popN(NumberRepository stack, int n) {
        LinkedList<NumberEntity> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            list.addFirst(stack.pop());
        }
        return list;
    }

    @Override
    public void applyOperator(Operator<NumberEntity> op) throws InsufficientParameterException {
        if (op instanceof UndoOperator) {
            undoOperation(numberRepo, operationLogRepository.removeLastLog());
        } else {
            operationLogRepository.addLog(doOperation(numberRepo, op));
        }
    }

    @Override
    public void pushNumber(NumberEntity number) {
        numberRepo.push(number);
        OperationLogVo<NumberEntity> log = OperationLogVo.<NumberEntity>builder()
                .poped(Collections.emptyList())
                .pushed(Arrays.asList(number)).build();
        operationLogRepository.addLog(log);
    }

    private OperationLogVo<NumberEntity> doOperation(NumberRepository stack, Operator<NumberEntity> op) {
        int n = op.paramSize(stack.size());
        if (stack.size() < n) throw new InsufficientParameterException();
        List<NumberEntity> args = this.popN(stack, n);
        List<NumberEntity> vals = op.compute(args);
        stack.addAll(vals);
        return OperationLogVo.<NumberEntity>builder().poped(args).pushed(vals).build();
    }

    private void undoOperation(NumberRepository stack, OperationLogVo<NumberEntity> log) {
        if (log == null) return;
        List<NumberEntity> toPush = log.getPoped();
        int toPop = log.getPushed().size();
        this.popN(stack, toPop);
        stack.addAll(toPush);
    }
}
