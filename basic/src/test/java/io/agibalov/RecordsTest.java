package io.agibalov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecordsTest {
    @Test
    public void canUseRecords() {
        Note note = new Note("note1", "Note One");
        assertEquals("note1", note.id());
        assertEquals("Note One", note.text());

        assertTrue(Note.class.isRecord());
    }

    public static record Note(String id, String text) {}
}
