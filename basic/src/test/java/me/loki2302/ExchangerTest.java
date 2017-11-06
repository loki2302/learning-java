package me.loki2302;

import org.junit.Test;

import java.util.concurrent.Exchanger;

import static org.junit.Assert.assertEquals;

public class ExchangerTest {
    @Test
    public void canUseExchanger() throws InterruptedException {
        Exchanger<Integer> arg1Exchanger = new Exchanger<>();
        Exchanger<Integer> arg2Exchanger = new Exchanger<>();
        Exchanger<Integer> resultExchanger = new Exchanger<>();

        new Thread(() -> {
            Integer result = null;
            try {
                int arg1 = arg1Exchanger.exchange(null);
                int arg2 = arg2Exchanger.exchange(null);

                result = arg1 + arg2;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultExchanger.exchange(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        arg1Exchanger.exchange(2);
        arg2Exchanger.exchange(3);
        int result = resultExchanger.exchange(null);
        assertEquals(5, result);
    }
}
