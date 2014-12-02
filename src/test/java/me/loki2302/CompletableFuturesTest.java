package me.loki2302;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CompletableFuturesTest {
    @Test
    public void canSupplyAsynchronously() throws ExecutionException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CompletableFuture<String> future = supplyAsync(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
            }

            return "hello";
        });

        try {
            future.get(1, TimeUnit.MILLISECONDS);
            fail();
        } catch(TimeoutException e) {
        }

        countDownLatch.countDown();

        String s = future.get();
        assertEquals("hello", s);
    }

    @Test
    public void canUseSpecificExecutor() throws ExecutionException, InterruptedException {
        AtomicInteger threadCount = new AtomicInteger();
        Executor executor = Executors.newFixedThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                threadCount.incrementAndGet();
                return new Thread(runnable);
            }
        });

        CompletableFuture<String> future = supplyAsync(() -> "hello", executor);
        future.get();

        assertEquals(1, threadCount.get());
    }
}
