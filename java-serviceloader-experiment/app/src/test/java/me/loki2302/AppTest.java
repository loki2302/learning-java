package me.loki2302;

import me.loki2302.codec.Codec;
import me.loki2302.codeca.CodecA;
import me.loki2302.codecb.CodecB;
import org.junit.Test;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class AppTest {
    @Test
    public void dummy() {
        ServiceLoader<Codec> codecServiceLoader = ServiceLoader.load(Codec.class);

        Map<Class<? extends Codec>, Codec> codecs = StreamSupport.stream(codecServiceLoader.spliterator(), false)
                .collect(Collectors.toMap(c -> c.getClass(), c -> c));
        assertEquals(2, codecs.size());
        assertEquals("Codec A", codecs.get(CodecA.class).describe());
        assertEquals("Codec B!!!", codecs.get(CodecB.class).describe());
    }
}
