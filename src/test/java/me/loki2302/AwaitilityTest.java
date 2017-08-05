package me.loki2302;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class AwaitilityTest {
    @Test
    public void dummy() {
        AtomicInteger someCounter = new AtomicInteger(0);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            for(int i = 0; i < 3; ++i) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}

                someCounter.incrementAndGet();
            }
        });

        await().until(() -> someCounter.get() == 3);
        await().until(() -> someCounter.get(), equalTo(3));
        await().untilAtomic(someCounter, equalTo(3));

        executorService.shutdown();

        assertEquals(3, someCounter.get());
    }
}
