package io.agibalov;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityQueueTest {
    @Test
    public void dummy() {
        PriorityQueue<Task> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(task -> task.priority));
        priorityQueue.add(new Task(2, "a"));
        priorityQueue.add(new Task(1, "b"));
        priorityQueue.add(new Task(3, "c"));
        priorityQueue.add(new Task(2, "d"));
        priorityQueue.add(new Task(3, "e"));

        String s = "";
        while(!priorityQueue.isEmpty()) {
            s += priorityQueue.poll();
        }

        assertEquals("1b2a2d3c3e", s);
    }

    public static class Task {
        public final int priority;
        public final String description;

        public Task(int priority, String description) {
            this.priority = priority;
            this.description = description;
        }

        @Override
        public String toString() {
            return String.format("%d%s", priority, description);
        }
    }
}
