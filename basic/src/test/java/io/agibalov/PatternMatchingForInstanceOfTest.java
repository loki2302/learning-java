package io.agibalov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternMatchingForInstanceOfTest {
    @Test
    public void canUsePatternMatchingForInstanceOf() {
        assertEquals("feature", getDescription(new Feature() {{ featureDescription = "feature"; }}));
        assertEquals("bug", getDescription(new Bug() {{ bugDescription = "bug"; }}));
    }

    private static String getDescription(Ticket ticket) {
        if (ticket instanceof Feature feature) {
            return feature.featureDescription;
        }

        if (ticket instanceof Bug bug) {
            return bug.bugDescription;
        }

        throw new RuntimeException();
    }

    private interface Ticket {
    }

    private static class Feature implements Ticket {
        public String featureDescription;
    }

    private static class Bug implements Ticket {
        public String bugDescription;
    }
}
