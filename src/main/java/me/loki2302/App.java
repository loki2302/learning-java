package me.loki2302;

public class App {
    public static void main(String[] args) {
        System.out.println("hello world");
        System.out.println(addNumbers(2, 3));
        System.out.println(addNumbers(3, 4));
    }

    private static int addNumbers(int a, int b) {
        return a + b;
    }
}
