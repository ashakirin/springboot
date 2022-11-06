package org.demo.awatility;


import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class DemoAwaitilityMainTest {
    private AsyncProcessing demoAwaitilityMain;

    @BeforeEach
    public void setUp() {
        Awaitility.setDefaultPollInterval(10, TimeUnit.MILLISECONDS);
        Awaitility.setDefaultPollDelay(Duration.ZERO);
        Awaitility.setDefaultTimeout(Duration.ONE_MINUTE);
        demoAwaitilityMain = new AsyncProcessing();
    }


    @Test
    public void testThreadAsync() {
        demoAwaitilityMain.submitTask();
        await()
                .atMost(10, TimeUnit.SECONDS)
                .with()
                .pollInterval(Duration.ONE_HUNDRED_MILLISECONDS)
                .until(demoAwaitilityMain::isStatusFinished);
    }

    @Test
    public void testParallelStreams() throws ExecutionException, InterruptedException {
        ForkJoinTask<Integer> forkJoinTask = demoAwaitilityMain.parallelStreams();
        await()
                .atMost(1, TimeUnit.SECONDS)
                .with()
                .pollInterval(Duration.ONE_HUNDRED_MILLISECONDS)
                .until(forkJoinTask::isCompletedNormally);
    }

    @Test
    public void ignoringExceptions() throws ExecutionException, InterruptedException {
        ForkJoinTask<Integer> forkJoinTask = demoAwaitilityMain.parallelStreams();
        await()
                .atMost(1, TimeUnit.SECONDS)
                .ignoreException(IllegalStateException.class)
                .with()
                .pollInterval(Duration.ONE_HUNDRED_MILLISECONDS)
                .until(() -> demoAwaitilityMain.getResult(forkJoinTask) > 0);

        assertThat(demoAwaitilityMain.getResult(forkJoinTask)).isEqualTo(76127);
    }

}