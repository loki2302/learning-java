package me.loki2302.dummyagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class DummyClassFileTransformer implements ClassFileTransformer {
    private final DummyAgent dummyAgent;
    private final String filterClassPrefix;

    public DummyClassFileTransformer(DummyAgent dummyAgent, String filterClassPrefix) {
        this.dummyAgent = dummyAgent;
        this.filterClassPrefix = filterClassPrefix;
    }

    @Override
    public byte[] transform(
            ClassLoader classLoader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] bytes) throws IllegalClassFormatException {

        String javaClassName = toJavaName(className);
        if(javaClassName.startsWith(filterClassPrefix)) {
            System.out.printf("Processing %s\n", javaClassName);

            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.get(javaClassName);
                CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
                for(CtMethod ctMethod : declaredMethods) {
                    String logBefore = dummyAgent.getLogBeforeCode(ctMethod.getName());
                    ctMethod.insertBefore(logBefore);

                    String logAfter = dummyAgent.getLogAfterCode(ctMethod.getName());
                    ctMethod.insertAfter(logAfter);
                }
                bytes = ctClass.toBytecode();
                ctClass.detach();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    private static String toJavaName(String className) {
        return className.replace('/', '.');
    }
}
