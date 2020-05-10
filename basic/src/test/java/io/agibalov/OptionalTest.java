package io.agibalov;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    public void canUseFlatMap() {
        // empty, because there's not value at all
        Optional<Integer> optional = Optional.ofNullable(null);
        Optional<Integer> flatMappedOptional = optional.flatMap(i -> Optional.of(i * 2));
        assertEquals(Optional.empty(), flatMappedOptional);

        // not empty, because there's a value
        optional = Optional.of(1);
        flatMappedOptional = optional.flatMap(i -> Optional.of(i * 2));
        assertEquals(Optional.of(2), flatMappedOptional);
    }

    @Test
    public void canUseMap() {
        // empty, because source value is empty
        assertEquals(Optional.empty(), Optional.<String>ofNullable(null).map(s -> s + "!"));

        // not empty, because source value is not empty and result is not empty
        assertEquals(Optional.of("hello!"), Optional.ofNullable("hello").map(s -> s + "!"));

        // empty, because result is null and gets translated to empty
        assertEquals(Optional.empty(), Optional.<String>ofNullable(null).map(s -> null));
    }

    @Test
    public void canUseIfPresent() {
        final Integer[] x = { null };
        Optional.<Integer>ofNullable(null).ifPresent(i -> {
            x[0] = i;
        });
        assertNull(x[0]);

        Optional.of(123).ifPresent(i -> {
            x[0] = i;
        });
        assertEquals(123, (int)x[0]);
    }

    @Test
    public void canUseOrElse() {
        assertEquals("hello", Optional.of("hello").orElse("<empty>"));
        assertEquals("<empty>", Optional.ofNullable(null).orElse("<empty>"));
    }

    @Test
    public void canUseOrElseGet() {
        assertEquals("hello", Optional.of("hello").orElseGet(() -> "<empty>"));
        assertEquals("<empty>", Optional.empty().orElseGet(() -> "<empty>"));
    }

    @Test
    public void canUseOrElseThrow() {
        try {
            Optional.empty().orElseThrow(() -> new RuntimeException("hello"));
        } catch (RuntimeException e) {
            assertEquals("hello", e.getMessage());
        }
    }
}
