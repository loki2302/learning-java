package me.loki2302;

import org.awaitility.core.ConditionEvaluationLogger;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.with;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AwaitilityTest {
    @Test
    public void shouldFailOnTimeout() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            TodoList todoList = new TodoList(executorService, 3000);

            todoList.addTodo("get coffee!!!");
            with()
                    .pollInterval(1, TimeUnit.SECONDS)
                    .timeout(2, TimeUnit.SECONDS)
                    .await().until(() -> todoList.getTodos(), hasSize(1));
            fail();
        } catch(ConditionTimeoutException e) {
            // TODO: intentionally blank
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void shouldWorkFineWhenThereIsEnoughTime() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            TodoList todoList = new TodoList(executorService, 3000);

            todoList.addTodo("get coffee!!!");
            Set<String> todos = with()
                    .pollInterval(1, TimeUnit.SECONDS)
                    .timeout(4, TimeUnit.SECONDS)
                    .await().until(() -> todoList.getTodos(), hasSize(1));
            assertEquals(1, todos.size());
            assertThat(todos, contains("get coffee!!!"));
        } finally {
            executorService.shutdown();
        }
    }

    private static class TodoList {
        private final ExecutorService executorService;
        private final int sleep;
        private final Set<String> todos = new HashSet<>();

        private TodoList(ExecutorService executorService, int sleep) {
            this.executorService = executorService;
            this.sleep = sleep;
        }

        public void addTodo(String todo) {
            executorService.submit(() -> {
                sleep();
                todos.add(todo);
            });
        }

        public Set<String> getTodos() {
            return new HashSet<>(todos);
        }

        private void sleep() {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }
}
