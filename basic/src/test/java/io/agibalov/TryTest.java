package io.agibalov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TryTest {
    @Test
    public void dummy() throws Exception {
        MyResourceStateHolder myResourceStateHolder = new MyResourceStateHolder();
        try(MyResource myResource = new MyResource(myResourceStateHolder)) {
            assertEquals(ResourceState.Initialized, myResourceStateHolder.resourceState);
        }

        assertEquals(ResourceState.Destroyed, myResourceStateHolder.resourceState);
    }

    private static class MyResource implements AutoCloseable {
        private final MyResourceStateHolder myResourceStateHolder;

        public MyResource(MyResourceStateHolder myResourceStateHolder) {
            this.myResourceStateHolder = myResourceStateHolder;
            myResourceStateHolder.resourceState = ResourceState.Initialized;
        }

        @Override
        public void close() throws Exception {
            myResourceStateHolder.resourceState = ResourceState.Destroyed;
        }
    }

    private static class MyResourceStateHolder {
        public ResourceState resourceState;
    }

    private enum ResourceState {
        Initialized,
        Destroyed
    }
}
