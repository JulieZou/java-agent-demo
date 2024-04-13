package com.ibicd;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;

public class MetricAgent {

    public static void premain(String args, Instrumentation inst){
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
                    Metric.printMemoryInfo();
                    Metric.printGCInfo();
                }, 0, 5000,
                java.util.concurrent.TimeUnit.MILLISECONDS);
    }
}
