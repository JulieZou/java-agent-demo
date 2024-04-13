package com.ibicd;

import java.lang.instrument.Instrumentation;

public class TraceAgent {

    public static void premain(String args, Instrumentation inst) {

        System.out.println("TraceAgent.premain: " + args);
    }
}
