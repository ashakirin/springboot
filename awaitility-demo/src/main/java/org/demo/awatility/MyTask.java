package org.demo.awatility;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public class MyTask implements Runnable {
    private final Consumer<String> callback;


    public MyTask(Consumer<String> callback) {
        this.callback = callback;
    }

    public void run() {
        IntStream.range(0, 100)
                .forEach(i -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        callback.accept("OK");
    }
}
