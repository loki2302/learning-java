package me.loki2302;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

public class CountDownLatchTest {
    @Test
    public void canUseCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            countDownLatch.countDown();
        }).start();

        countDownLatch.await();
        assertEquals(0, countDownLatch.getCount());
    }
}
