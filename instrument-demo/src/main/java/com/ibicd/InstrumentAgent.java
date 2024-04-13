package com.ibicd;

import java.lang.instrument.Instrumentation;

public class InstrumentAgent {

    public static void premain(String args, Instrumentation inst) {
        System.out.println("premain: 获取方法的调用时间");
        inst.addTransformer(new InstrumentMonitorTransformer());
    }
}
