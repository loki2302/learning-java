package io.agibalov;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListIteratorTest {
    @Test
    public void canUseListIteratorToGoNext() {
        List<Integer> items = Arrays.asList(11, 22, 33);
        ListIterator<Integer> it = items.listIterator(1);

        assertTrue(it.hasNext());
        assertTrue(it.hasPrevious());
        assertEquals(22, (int)it.next());
    }

    @Test
    public void canUseListIteratorToGoPrevious() {
        List<Integer> items = Arrays.asList(11, 22, 33);
        ListIterator<Integer> it = items.listIterator(1);

        assertTrue(it.hasNext());
        assertTrue(it.hasPrevious());
        assertEquals(11, (int)it.previous());
    }

    @Test
    public void canUseListIteratorToSetItem() {
        List<Integer> items = new ArrayList<>(Arrays.asList(11, 22, 33));
        ListIterator<Integer> it = items.listIterator(1);
        it.next();
        it.set(888);

        assertEquals(Arrays.asList(11, 888, 33), items);
    }

    @Test
    public void canUseListIteratorToAddItem() {
        List<Integer> items = new ArrayList<>(Arrays.asList(11, 22, 33));
        ListIterator<Integer> it = items.listIterator(1);
        it.add(888);

        assertEquals(Arrays.asList(11, 888, 22, 33), items);
    }
}
