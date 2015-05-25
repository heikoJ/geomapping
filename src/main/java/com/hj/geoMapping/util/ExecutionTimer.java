package com.hj.geoMapping.util;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by heiko on 25.05.15.
 */
public class ExecutionTimer {

    private String description;

    private PrintStream out;


    private long startTimeInMillis;

    public static ExecutionTimer timer(PrintStream stream,String description) {
        return new ExecutionTimer(stream,description);
    }

    private ExecutionTimer(PrintStream out,String description) {
        this.out = out;
        this.description = description;
    }

    public ExecutionTimer start() {
        startTimeInMillis = System.currentTimeMillis();
        return this;
    }

    public void stopAndPrintResults() {
        long endTimeInMillis = System.currentTimeMillis();
        long runtime = endTimeInMillis - startTimeInMillis;

        out.println("Execution of " + description + " took " + runtime + " milliseconds");
    }


}
