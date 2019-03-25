package me.loki2302;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

public class ParallelStreamTest {
    @Test
    public void parallelStreamUsesMoreThanOneThread() {
        Set<String> threadNames = new HashSet<>();
        Arrays.asList(11, 22, 33, 44, 55, 66, 77, 88, 99).parallelStream()
                .forEach(x -> threadNames.add(Thread.currentThread().getName()));
        assertTrue(threadNames.size() > 1);
    }

    // This test is unstable - where I expect exactly N threads, it should be "at most N"
    @Test
    public void canUseCustomThreadPoolForParallelStream() throws ExecutionException, InterruptedException {
        List<Integer> items = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

        {
            ForkJoinPool forkJoinPool =
                    new ForkJoinPool(1, new MyForkJoinWorkerThreadFactory(), null, false);
            Set<String> threadNames = Collections.synchronizedSet(new HashSet<>());

            forkJoinPool.submit(() ->
                    items.parallelStream().forEach(x -> threadNames.add(Thread.currentThread().getName()))
            ).get();
            assumeTrue(threadNames.size() == 1);
            assertEquals(new HashSet<>(Arrays.asList(
                    "thread-1")), threadNames);
        }

        {
            ForkJoinPool forkJoinPool =
                    new ForkJoinPool(2, new MyForkJoinWorkerThreadFactory(), null, false);
            Set<String> threadNames = Collections.synchronizedSet(new HashSet<>());

            forkJoinPool.submit(() ->
                    items.parallelStream().forEach(x -> threadNames.add(Thread.currentThread().getName()))
            ).get();
            assumeTrue(threadNames.size() == 2);
            assertEquals(new HashSet<>(Arrays.asList(
                    "thread-1",
                    "thread-2")), threadNames);
        }

        {
            ForkJoinPool forkJoinPool =
                    new ForkJoinPool(3, new MyForkJoinWorkerThreadFactory(), null, false);
            Set<String> threadNames = Collections.synchronizedSet(new HashSet<>());

            forkJoinPool.submit(() ->
                    items.parallelStream().forEach(x -> threadNames.add(Thread.currentThread().getName()))
            ).get();
            assumeTrue(threadNames.size() == 3);
            assertEquals(new HashSet<>(Arrays.asList(
                    "thread-1",
                    "thread-2",
                    "thread-3")), threadNames);
        }

        {
            ForkJoinPool forkJoinPool =
                    new ForkJoinPool(4, new MyForkJoinWorkerThreadFactory(), null, false);
            Set<String> threadNames = Collections.synchronizedSet(new HashSet<>());

            forkJoinPool.submit(() ->
                    items.parallelStream().forEach(x -> threadNames.add(Thread.currentThread().getName()))
            ).get();
            assumeTrue(threadNames.size() == 4);
            assertEquals(new HashSet<>(Arrays.asList(
                    "thread-1",
                    "thread-2",
                    "thread-3",
                    "thread-4")), threadNames);
        }
    }

    private class MyForkJoinWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {
        private int index = 0;

        @Override
        public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
            ++index;

            ForkJoinWorkerThread forkJoinWorkerThread = new ForkJoinWorkerThread(pool) {};
            forkJoinWorkerThread.setName(String.format("thread-%d", index));
            return forkJoinWorkerThread;
        }
    }
}
