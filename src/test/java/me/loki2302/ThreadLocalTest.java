package me.loki2302;

import org.junit.Test;

import java.util.concurrent.Exchanger;

import static org.junit.Assert.assertEquals;

public class ThreadLocalTest {
    @Test
    public void canUseThreadLocal() throws InterruptedException {
        ThreadLocalHolder threadLocalHolder = new ThreadLocalHolder();

        Exchanger<String> thread1Exchanger = new Exchanger<>();
        new Thread(() -> {
            threadLocalHolder.setString("thread one");
            try {
                thread1Exchanger.exchange(threadLocalHolder.getString());
            } catch (InterruptedException e) {
            }
        }).start();

        Exchanger<String> thread2Exchanger = new Exchanger<>();
        new Thread(() -> {
            threadLocalHolder.setString("thread two");
            try {
                thread2Exchanger.exchange(threadLocalHolder.getString());
            } catch (InterruptedException e) {
            }
        }).start();

        assertEquals("thread one", thread1Exchanger.exchange(null));
        assertEquals("thread two", thread2Exchanger.exchange(null));
        assertEquals(null, threadLocalHolder.getString());
    }

    private static class ThreadLocalHolder {
        private final ThreadLocal<String> threadLocalString = new ThreadLocal<>();

        public void setString(String s) {
            threadLocalString.set(s);
        }

        public String getString() {
            return threadLocalString.get();
        }
    }
}
