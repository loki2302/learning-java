package me.loki2302;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("hello world");

        MainLibrary mainLibrary = (MainLibrary)Native.loadLibrary("build/binaries/mainSharedLibrary/libmain.so", MainLibrary.class);
        System.out.printf("Native library says: %d\n", mainLibrary.addNumbers(2, 3));

        AddNumbersRequest request = new AddNumbersRequest();
        request.a = 3;
        request.b = 4;
        AddNumbersResponse response = mainLibrary.addNumbersStruct(request);
        try {
            System.out.printf("Native library says: %d\n", response.result);
        } finally {
            mainLibrary.freeAddNumbersResponse(response);
        }
    }

    public interface MainLibrary extends Library {
        int addNumbers(int a, int b);
        AddNumbersResponse addNumbersStruct(AddNumbersRequest request);
        void freeAddNumbersResponse(AddNumbersResponse response);
    }

    public static class AddNumbersRequest extends Structure {
        public int a;
        public int b;

        @Override
        protected List getFieldOrder() {
            return Arrays.asList("a", "b");
        }
    }

    public static class AddNumbersResponse extends Structure {
        public int result;

        @Override
        protected List getFieldOrder() {
            return Arrays.asList("result");
        }
    }
}
