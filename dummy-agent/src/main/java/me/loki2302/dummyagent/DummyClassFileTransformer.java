package me.loki2302.dummyagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class DummyClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(
            ClassLoader classLoader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] bytes) throws IllegalClassFormatException {

        String javaClassName = toJavaName(className);
        if(javaClassName.equals("me.loki2302.App")) {
            System.out.printf("Processing %s\n", javaClassName);

            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.get(javaClassName);
                CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
                for(CtMethod ctMethod : declaredMethods) {
                    ctMethod.insertBefore("System.out.println(\"BEFORE " + ctMethod.getLongName() + "\");");
                    ctMethod.insertAfter("System.out.println(\"AFTER " + ctMethod.getLongName() + "\");");
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
