package me.loki2302;

import com.sun.jna.Native;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {
    private App.MainLibrary mainLibrary;

    @Before
    public void initLibrary() {
        mainLibrary = (App.MainLibrary) Native.loadLibrary("build/binaries/mainSharedLibrary/libmain.so", App.MainLibrary.class);
    }

    @Test
    public void canAddNumbers() {
        assertEquals(5, mainLibrary.addNumbers(2, 3));
    }

    @Test
    public void canAddNumbersStruct() {
        App.AddNumbersRequest request = new App.AddNumbersRequest();
        request.a = 3;
        request.b = 4;

        App.AddNumbersResponse response = mainLibrary.addNumbersStruct(request);
        try {
            assertEquals(7, response.result);
        } finally {
            mainLibrary.freeAddNumbersResponse(response);
        }
    }
}
