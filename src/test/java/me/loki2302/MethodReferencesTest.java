package me.loki2302;

import org.junit.Test;

import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MethodReferencesTest {
    @Test
    public void canReferenceStaticMethod() {
        BiFunction<Integer, Integer, Integer> staticMethodReference = Calculator::addNumbersStatic;
        assertEquals(3, (int) staticMethodReference.apply(1, 2));
    }

    @Test
    public void canReferenceInstanceMethod() {
        Calculator calculator = new Calculator();
        BiFunction<Integer, Integer, Integer> instanceMethodReference = calculator::addNumbersInstance;
        assertEquals(3, (int) instanceMethodReference.apply(1, 2));
    }

    @Test
    public void canReferenceConstructor() {
        Supplier<HashSet<Integer>> constructorReference = HashSet<Integer>::new;
        HashSet<Integer> hashSet = constructorReference.get();
        assertTrue(hashSet.isEmpty());
    }

    private static class Calculator {
        public static int addNumbersStatic(int a, int b) {
            return a + b;
        }

        public int addNumbersInstance(int a, int b) {
            return a + b;
        }
    }
}
