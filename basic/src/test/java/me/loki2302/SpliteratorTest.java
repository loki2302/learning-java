package me.loki2302;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

import static org.junit.Assert.*;

public class SpliteratorTest {
    @Test
    public void canUseOneSpliterator() {
        List<Integer> items = Arrays.asList(11, 22, 33);
        Spliterator<Integer> spliterator = items.spliterator();

        assertEquals(11, (int)doTryAdvance(spliterator));
        assertEquals(22, (int)doTryAdvance(spliterator));
        assertEquals(33, (int)doTryAdvance(spliterator));
        assertNull(doTryAdvance(spliterator));
    }

    @Test
    public void canUseMultipleSpliterators() {
        List<Integer> items = Arrays.asList(11, 22, 33, 44, 55, 66, 77);
        Spliterator<Integer> spliterator1 = items.spliterator();
        assertEquals(11, (int)doTryAdvance(spliterator1));
        assertEquals(22, (int)doTryAdvance(spliterator1));

        Spliterator<Integer> spliterator2 = spliterator1.trySplit();
        assertNotNull(spliterator2);
        assertEquals(55, (int)doTryAdvance(spliterator1));
        assertEquals(33, (int)doTryAdvance(spliterator2));
        assertEquals(66, (int)doTryAdvance(spliterator1));
        assertEquals(44, (int)doTryAdvance(spliterator2));

        assertNull(spliterator2.trySplit());
        assertEquals(77, (int)doTryAdvance(spliterator1));
        assertNull(doTryAdvance(spliterator2));
    }

    private <T> T doTryAdvance(Spliterator<T> spliterator) {
        Object[] dummy = new Object[1];
        spliterator.tryAdvance(i -> dummy[0] = i);
        return (T)dummy[0];
    }
}
