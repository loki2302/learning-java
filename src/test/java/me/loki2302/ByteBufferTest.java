package me.loki2302;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

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
}
