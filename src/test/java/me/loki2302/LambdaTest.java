package me.loki2302;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LambdaTest {
    @Test
    public void dummy() {
        Func0 getOne = () -> 1;
        assertEquals(1, getOne.getValue());

        Func1 twice = x -> 2 * x;
        assertEquals(2, twice.processInt(1));

        Func2 add = (x, y) -> x + y;
        assertEquals(3, add.process2Ints(1, 2));
    }

    private static interface Func0 {
        int getValue();
    }

    private static interface Func1 {
        int processInt(int value);
    }

    private static interface Func2 {
        int process2Ints(int left, int right);
    }
}
