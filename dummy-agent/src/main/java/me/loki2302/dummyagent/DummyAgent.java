package me.loki2302.dummyagent;

import java.lang.instrument.Instrumentation;

public class DummyAgent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new DummyClassFileTransformer());
    }
}
