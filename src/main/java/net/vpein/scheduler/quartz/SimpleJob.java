package net.vpein.scheduler.quartz;

import lombok.extern.java.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Log
public class SimpleJob implements Job {

    private final AtomicInteger ai = new AtomicInteger(0);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        long thisHash = hash("MyJob"+ai.getAndIncrement()+"IsGoingToBeExecutedAt"+ LocalDateTime.now().toString());

        log.info("simple job executed ("+String.valueOf(thisHash)+")");

        long sleepTimer = (long)(Math.random() * 10000 + 1);
        log.info("Sleep for " + sleepTimer + " for "+String.valueOf(thisHash));

        try {
            Thread.sleep(sleepTimer);
        } catch (InterruptedException e) {
            log.severe("InterruptedException : " + e.toString() + " for "+String.valueOf(thisHash));
        }

        log.info("Result: " + (int)(Math.random() * 100 + 1) + " for " + String.valueOf(thisHash));

    }

    // adapted from String.hashCode()
    public static long hash(String string) {
        long h = 1125899906842597L; // prime
        int len = string.length();

        for (int i = 0; i < len; i++) {
            h = 31*h + string.charAt(i);
        }
        return h;
    }

}