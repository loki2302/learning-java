package me.loki2302.dummyagent;

import java.lang.instrument.Instrumentation;

public class PreMain {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        DummyAgent dummyAgent = new DummyAgent();
        dummyAgent.deploy();

        instrumentation.addTransformer(new DummyClassFileTransformer(dummyAgent, agentArgs));
    }
}
