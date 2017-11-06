package me.loki2302;

import org.junit.Test;

import java.util.Arrays;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class TreeSetTest {
    @Test
    public void dummy() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.addAll(Arrays.asList(1, 5, 4, 3, 9));
        assertEquals(9, treeSet.higher(5).intValue());
        assertEquals(1, treeSet.lower(3).intValue());
    }
}
