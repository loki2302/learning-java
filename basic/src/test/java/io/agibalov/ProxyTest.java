package io.agibalov;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProxyTest {
    @Test
    public void dummy() {
        Calculator calculator = new CalculatorImpl();
        DummyInvocationHandler dummyInvocationHandler = new DummyInvocationHandler(calculator);

        Calculator calculatorProxy = (Calculator)Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(),
                new Class<?>[] { Calculator.class },
                dummyInvocationHandler);

        int result = calculatorProxy.addNumbers(2, 3);
        assertEquals(5, result);

        assertEquals(1, dummyInvocationHandler.getInvocationCount());
    }

    public interface Calculator {
        int addNumbers(int x, int y);
    }

    public static class CalculatorImpl implements Calculator {
        public int addNumbers(int x, int y) {
            return x + y;
        }
    }

    public static class DummyInvocationHandler implements InvocationHandler {
        private final Object target;
        private int invocationCount;

        public DummyInvocationHandler(Object target) {
            this.target = target;
        }

        public int getInvocationCount() {
            return invocationCount;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] args) throws Throwable {
            ++invocationCount;
            Object result = method.invoke(target, args);
            return result;
        }
    }
}
