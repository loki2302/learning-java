package me.loki2302;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CompletableFuturesTest {
    @Test
    public void canSupplyResultWithSupplier() throws ExecutionException, InterruptedException {
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
    public void canSupplyResultWithCompletableFutureComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
            }

            future.complete("hello");
        }).start();

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
        Executor executor = Executors.newFixedThreadPool(1, runnable -> {
            threadCount.incrementAndGet();
            return new Thread(runnable);
        });

        CompletableFuture<String> future = supplyAsync(() -> "hello", executor);
        future.get();

        assertEquals(1, threadCount.get());
    }

    @Test
    public void canWaitFor2Futures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> helloFuture = supplyAsync(() -> "hello");
        CompletableFuture<String> worldFuture = supplyAsync(() -> "world");

        String helloWorldString = helloFuture.thenCombineAsync(
                worldFuture,
                (helloString, worldString) -> helloString + " " + worldString).get();

        assertEquals("hello world", helloWorldString);
    }

    @Test
    public void dependentFutureThrowsWhenOneOfItsDependenciesThrow() throws ExecutionException, InterruptedException {
        CompletableFuture<String> helloFuture = supplyAsync(() -> "hello");
        CompletableFuture<String> worldFuture = supplyAsync(() -> {
            throw new RuntimeException("hello exception");
        });

        try {
            helloFuture.thenCombineAsync(
                    worldFuture,
                    (helloString, worldString) -> helloString + " " + worldString).get();
            fail();
        } catch (ExecutionException e) {
            assertEquals("hello exception", e.getCause().getMessage());
        }
    }

    @Test
    public void canThrowExceptionsAsynchronously() throws InterruptedException {
        CompletableFuture<Object> future = supplyAsync(() -> {
            throw new RuntimeException("hello exception");
        });

        try {
            future.get();
            fail();
        } catch (ExecutionException e) {
            assertEquals("hello exception", e.getCause().getMessage());
        }

        try {
            future.join();
            fail();
        } catch(CompletionException e) {
            assertEquals("hello exception", e.getCause().getMessage());
        }

        assertTrue(future.isDone());
        assertTrue(future.isCompletedExceptionally());
    }
}
