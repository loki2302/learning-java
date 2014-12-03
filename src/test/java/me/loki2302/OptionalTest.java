package me.loki2302;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.*;

public class OptionalTest {
    @Test
    public void canUseOfNullable() {
        Optional<String> optional = Optional.ofNullable(null);
        assertEquals(Optional.empty(), optional);
        assertFalse(optional.isPresent());

        optional = Optional.ofNullable("hello");
        assertEquals("hello", optional.get());
        assertTrue(optional.isPresent());
    }

    @Test
    public void optionalThrowsWhenTryingToGetValueOfEmptyOptional() {
        Optional<String> emptyOptional = Optional.ofNullable(null);
        try {
            emptyOptional.get();
            fail();
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void optionalThrowsWhenSupplyingNullValueToOf() {
        try {
            Optional.of(null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void canUseFilter() {
        // empty, because there's no value at all
        Optional<Integer> optional = Optional.ofNullable(null);
        Optional<Integer> filteredOptional = optional.filter(i -> i % 2 == 0);
        assertEquals(Optional.empty(), filteredOptional);

        //empty, because doesn't match the predicate
        optional = Optional.of(1);
        filteredOptional = optional.filter(i -> i % 2 == 0);
        assertEquals(Optional.empty(), filteredOptional);

        // not empty, because not empty and matches the predicate
        optional = Optional.of(2);
        filteredOptional = optional.filter(i -> i % 2 == 0);
        assertEquals(optional, filteredOptional);
    }

    @Test
    public void canUseOrElse() {
        Function<String, String> getOriginalOrEmpty = s -> Optional.ofNullable(s).orElse("<empty>");

        assertEquals("hello", getOriginalOrEmpty.apply("hello"));
        assertEquals("<empty>", getOriginalOrEmpty.apply(null));
    }
}
