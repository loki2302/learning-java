package me.loki2302;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class StreamTest {
    @Test
    public void canUseMap() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Integer> doubledNumbers = numbers.stream()
                .map(x -> 2 * x)
                .collect(Collectors.toList());
        assertEquals(3, doubledNumbers.size());
        assertEquals(2, (int)doubledNumbers.get(0));
        assertEquals(4, (int)doubledNumbers.get(1));
        assertEquals(6, (int)doubledNumbers.get(2));
    }

    @Test
    public void canUseReduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        int sum = numbers.stream().reduce(0, (s, x) -> s + x);
        assertEquals(6, sum);
    }
}
