package io.agibalov;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteBufferTest {
    @Test
    public void dummy() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.put((byte)1);
        byteBuffer.put((byte)2);
        byteBuffer.putShort((short)3);
        byteBuffer.putInt(4);
        byteBuffer.putLong(5L);
        byte[] data = byteBuffer.array();
        assertEquals(16, data.length);

        ByteBuffer byteBuffer2 = ByteBuffer.wrap(data);
        assertEquals((byte)1, byteBuffer2.get());
        assertEquals((byte)2, byteBuffer2.get());
        assertEquals((short)3, byteBuffer2.getShort());
        assertEquals(4, byteBuffer2.getInt());
        assertEquals(5L, byteBuffer2.getLong());
    }

    @Test
    public void canUseBigEndian() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.putInt(0xaabbccdd);
        byte[] array = byteBuffer.array();
        assertEquals((byte)0xaa, array[0]);
        assertEquals((byte)0xbb, array[1]);
        assertEquals((byte)0xcc, array[2]);
        assertEquals((byte)0xdd, array[3]);
    }

    @Test
    public void canUseLittleEndian() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(0xaabbccdd);
        byte[] array = byteBuffer.array();
        assertEquals((byte)0xdd, array[0]);
        assertEquals((byte)0xcc, array[1]);
        assertEquals((byte)0xbb, array[2]);
        assertEquals((byte)0xaa, array[3]);
    }
}
