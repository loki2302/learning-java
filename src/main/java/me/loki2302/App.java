package me.loki2302;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class App {
    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("me.loki2302:type=Dummy");
        Dummy dummy = new Dummy();
        mBeanServer.registerMBean(dummy, objectName);

        System.out.printf("PID: %s\n", ManagementFactory.getRuntimeMXBean().getName());

        Thread.sleep(Long.MAX_VALUE);
    }

    public interface DummyMBean {
        void sayHello();
        void saySomething(String what);
        int addNumbers(int a, int b);
        int getMagicNumber();
    }

    public static class Dummy implements DummyMBean {
        @Override
        public void sayHello() {
            System.out.printf("DummyMBean says HELLO\n");
        }

        @Override
        public void saySomething(String what) {
            System.out.printf("DummyMBean says %s\n", what);
        }

        @Override
        public int addNumbers(int a, int b) {
            return  a + b;
        }

        @Override
        public int getMagicNumber() {
            return 42;
        }
    }
}
