package io.agibalov;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamTest {
    @Test
    public void canUseMap() {
        Stream<Integer> numbers = Stream.of(1, 2, 3);
        List<Integer> doubledNumbers = numbers
                .map(x -> 2 * x)
                .collect(Collectors.toList());
        assertEquals(3, doubledNumbers.size());
        assertEquals(2, (int)doubledNumbers.get(0));
        assertEquals(4, (int)doubledNumbers.get(1));
        assertEquals(6, (int)doubledNumbers.get(2));
    }

    @Test
    public void canUseFlatMap() {
        Stream<List<Integer>> lists = Stream.of(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5));
        List<Integer> numbers = lists
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());
        assertEquals(5, numbers.size());
        assertEquals(1, (int)numbers.get(0));
        assertEquals(2, (int)numbers.get(1));
        assertEquals(3, (int)numbers.get(2));
        assertEquals(4, (int)numbers.get(3));
        assertEquals(5, (int)numbers.get(4));
    }

    @Test
    public void canUseReduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        int sum = numbers.stream().reduce(0, (s, x) -> s + x);
        assertEquals(6, sum);
    }

    @Test
    public void canUseFilter() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Integer> oddNumbers = numbers.stream().filter(n -> n % 2 != 0).collect(Collectors.toList());
        assertEquals(2, oddNumbers.size());
        assertEquals(1, (int)oddNumbers.get(0));
        assertEquals(3, (int)oddNumbers.get(1));
    }
}
