package me.loki2302;

import me.loki2302.codec.Codec;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class App {
    public static void main(String[] args) {
        ServiceLoader<Codec> codecServiceLoader = ServiceLoader.load(Codec.class);

        StreamSupport.stream(codecServiceLoader.spliterator(), false).forEach(s -> {
            System.out.printf("%s says: '%s'\n", s, s.describe());
        });
    }
}
