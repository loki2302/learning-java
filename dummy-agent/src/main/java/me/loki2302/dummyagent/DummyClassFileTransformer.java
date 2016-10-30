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

        if(className.equals("me/loki2302/App")) {
            System.out.printf("me.loki2302.dummyagent.DummyClassFileTransformer className=%s\n", className);

            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.get("me.loki2302.App");
                CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
                for(CtMethod ctMethod : declaredMethods) {
                    //CtMethod ctMethod = ctClass.getDeclaredMethod("main");
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
}
