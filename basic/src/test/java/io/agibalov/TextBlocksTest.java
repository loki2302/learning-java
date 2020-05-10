package io.agibalov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextBlocksTest {
    @Test
    public void canUseTextBlocks() {
        String s = """
                hello
                world""";
        assertEquals("hello\nworld", s);
    }

    @Test
    public void canUseFormatted() {
        String s = """
                hello
                %s""".formatted("WORLD");
        assertEquals("hello\nWORLD", s);
    }
}
