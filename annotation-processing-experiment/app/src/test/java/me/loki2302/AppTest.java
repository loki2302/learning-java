package me.loki2302;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {
    @Test
    public void test() {
        SomethingFactory factory = new SomethingFactory();
        Something another1 = factory.make();
        Something another2 = factory.make();
        assertFalse(another1 == another2);
    }    
}
