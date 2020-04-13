package me.jersey.calculator;

import lombok.extern.slf4j.Slf4j;
import me.jersey.calculator.calculator.RpnCalculator;
import me.jersey.calculator.operator.OperatorFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class RpnCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RpnCalculatorApplication.class);
        ConfigurableApplicationContext context = app.run(args);
        RpnCalculator calculator = context.getBean(RpnCalculator.class);
        OperatorFactory operatorFactory = context.getBean(OperatorFactory.class);
        System.out.println("== RPN Calculator ==");
        System.out.println("Usage: \n\tinput \"help\" to see all supported operators; input \"exit\" to exit.");
        Runtime.getRuntime().addShutdownHook(new GracefullyShutdown());

        Scanner scanner = new Scanner(System.in);
        String line;
        while ((line = scanner.nextLine()) != null) {
            if (line.equalsIgnoreCase("exit")) {
                context.close();
                System.exit(0);
            } else if (line.equalsIgnoreCase("help")) {
                System.out.println("supported operators: " + Arrays.toString(operatorFactory.supportedOperators().toArray()));
            } else {
                calculator.takeInput(line);
            }
        }
    }
}