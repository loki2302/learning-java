package me.loki2302;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class OptionalTest {
    @Test
    public void dummy() {
        Function<String, String> getOriginalOrEmpty = s -> Optional.ofNullable(s).orElse("<empty>");

        assertEquals("hello", getOriginalOrEmpty.apply("hello"));
        assertEquals("<empty>", getOriginalOrEmpty.apply(null));
    }
}
