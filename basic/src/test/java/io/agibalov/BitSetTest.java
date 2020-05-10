package io.agibalov;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BitSetTest {
    @Test
    public void dummy() {
        BitSet bitSet = new BitSet();
        bitSet.set(0, true);
        bitSet.set(1, false);
        bitSet.set(2, true);
        bitSet.set(3, false);
        bitSet.set(4, false);
        bitSet.set(5, false);
        bitSet.set(6, true);
        bitSet.set(7, false);

        byte[] bytes = bitSet.toByteArray();
        assertArrayEquals(new byte[] { 69 }, bytes);
    }
}
