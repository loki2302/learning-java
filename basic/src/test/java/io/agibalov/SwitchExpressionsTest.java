package io.agibalov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SwitchExpressionsTest {
    @Test
    public void canUseSwitchExpressions() {
        assertEquals("one", getStringByNumber(1));
        assertEquals("two", getStringByNumber(2));
        assertEquals("three or four", getStringByNumber(3));
        assertEquals("three or four", getStringByNumber(4));
        assertEquals("five", getStringByNumber(5));
        assertEquals("six", getStringByNumber(6));
        try {
            getStringByNumber(0);
            fail();
        } catch (RuntimeException e) {
            assertEquals("Unknown", e.getMessage());
        }
    }

    private static String getStringByNumber(int i) {
        return switch (i) {
            case 1 -> "one";
            case 2 -> "two";
            case 3, 4 -> "three or four";
            case 5, 6 -> {
                if (i == 5) {
                    yield "five";
                }
                yield "six";
            }
            default -> throw new RuntimeException("Unknown");
        };
    }
}
