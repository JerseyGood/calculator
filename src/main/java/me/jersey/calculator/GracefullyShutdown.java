package me.jersey.calculator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GracefullyShutdown extends Thread  {
    @Override
    public void run() {
        // clean up resources
        // persist in memory data if needed.
        log.info("about to shutdown...");
        System.out.println("== Bye ==");

    }
}
