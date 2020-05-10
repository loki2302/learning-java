package io.agibalov;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IteratorTest {
    @Test
    public void canUseIterator() {
        List<Integer> items = Arrays.asList(11, 22, 33);
        Iterator<Integer> it = items.iterator();
        assertTrue(it.hasNext());
        assertEquals(11, (int)it.next());
        assertTrue(it.hasNext());
        assertEquals(22, (int)it.next());
        assertTrue(it.hasNext());
        assertEquals(33, (int)it.next());
        assertFalse(it.hasNext());
    }
}
